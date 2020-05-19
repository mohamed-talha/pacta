package ma.uca.pacta.dao;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.filter.CompareFilter.CompareOp;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.RowFilter;
import org.apache.hadoop.hbase.filter.SubstringComparator;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import ma.uca.pacta.beans.Infection;
import ma.uca.pacta.beans.InfectionRegistrationReply;
import ma.uca.pacta.utils.ZookeeperConnection;

public class InfectionDAO {
	final static Logger logger = Logger.getLogger(InfectionDAO.class);
	Configuration config;
	Connection connection;
	Table accountTable;
	Table contactTable;

	private static InfectionDAO infectionDAOInstance = null;
	
	// Singleton : one instance is sufficient to do the job
	public static InfectionDAO getInstance() {
		if (infectionDAOInstance == null) {
			infectionDAOInstance = new InfectionDAO();
			return infectionDAOInstance;
		} else {
			return infectionDAOInstance;
		}
	}

	private InfectionDAO() {
		// Configuration
		config = ZookeeperConnection.config;
		
		//Connection
		try {
			connection = ConnectionFactory.createConnection(config);
		} catch (IOException e) {
			logger.error("Failed to open connection for InfectionDAO!", e);
		}
	}
	
	private void openContactTable() {
		try {
			if (contactTable == null) {
				contactTable = connection.getTable(TableName.valueOf("contact"));					
			}			
		} catch (IOException e) {
			logger.error("Failed to open contact table for infection reporting!", e);
		}
	}

	private void openAccountTable() {
		try {
			if (accountTable == null) {
				accountTable = connection.getTable(TableName.valueOf("account"));					
			}			
		} catch (IOException e) {
			logger.error("Failed to open account table for infection reporting!", e);
		}
	}

	private void closeContactTable() {
		try {
			if (contactTable != null) {
				contactTable.close();
			}
		} catch (IOException e) {
			logger.error("Failed to close contact table for infection reporting!", e);
		}
	}

	private void closeAccountTable() {
		try {
			if (accountTable != null) {
				accountTable.close();
			}
		} catch (IOException e) {
			logger.error("Failed to close account table for infection reporting!", e);
		}
	}

	/*
	 * Report new infection will be in five steps :
	 * 		1. Update Account Table for the infected user
	 * 		2. Look for its direct contacts, in Contact Table, during the last 21 days
	 * 		3. Look for its indirect contacts, in Contact Table, during the last 21 days
	 * 		4. Update Account Table for all direct contacts
	 * 		5. Update Account Table for all indirect contacts
	 */
	public InfectionRegistrationReply reportInfection(Infection infection) throws IOException {
		if (infection == null) return null;
		logger.debug("Start reporting new infection: " + infection.toString() + "...");
		InfectionRegistrationReply infectionRegistrationReply = null;
		
		try {
			//--------------- Step 1 : Update Account Table for the infected user ------------//
			//Connect to Account Table
			openAccountTable();
			
			// instantiate Get class : need to know if the contact is already stored
			Get g = new Get(Bytes.toBytes(infection.getInfectionUserId()));

			// get the Result object
			Result result = accountTable.get(g);			
			if (result == null || result.isEmpty()) {//accountId does not exist
				//Failure
				infectionRegistrationReply = new InfectionRegistrationReply(infection.getInfectionUserId(), infection.getInfectionDate(), infection.getInfectionReporterId(), "null", "null", "No account found for the infected user", 400);
				logger.debug("Failure to report new infection, no account found!");
			} else {
				// read values from Result class object
				byte[] numberOfDirectInfectedContact = result.getValue(Bytes.toBytes("contact"), Bytes.toBytes("numberOfDirectInfectedContact"));
				byte[] numberOfIndirectInfectedContact = result.getValue(Bytes.toBytes("contact"), Bytes.toBytes("numberOfIndirectInfectedContact"));

				// instantiate Put class
				Put p = new Put(Bytes.toBytes(infection.getInfectionUserId()));

				// add values using addColumn() method
				p.addColumn(Bytes.toBytes("contact"), Bytes.toBytes("numberOfDirectInfectedContact"), numberOfDirectInfectedContact);
				p.addColumn(Bytes.toBytes("contact"), Bytes.toBytes("numberOfIndirectInfectedContact"), numberOfIndirectInfectedContact);
				p.addColumn(Bytes.toBytes("infection"), Bytes.toBytes("isInfected"), Bytes.toBytes(true));
				p.addColumn(Bytes.toBytes("infection"), Bytes.toBytes("infectedOn"), Bytes.toBytes(infection.getInfectionDate()));
				p.addColumn(Bytes.toBytes("infection"), Bytes.toBytes("infectionDeclaredBy"),Bytes.toBytes(infection.getInfectionReporterId()));

				// save the put Instance to the HTable.
				accountTable.put(p);
				logger.debug("Step 1, Infected account updated successfully!");

				//--------------- Step 2: Look for direct contacts, in Contact Table, during the last 21 days ------------//
				openContactTable();
				LocalDate infectionDate = Instant.ofEpochSecond(infection.getInfectionDate()).atZone(ZoneId.systemDefault()).toLocalDate();
				Set<String> directContactIds = getDirectContactIds(infection.getInfectionUserId(), infectionDate);				
				logger.debug("Step 2, direct contacts for contact [" + infection.getInfectionUserId() + "] : " + directContactIds);
				
				//--------------- Step 3: Look for indirect contacts, in Contact Table, during the last 21 days ------------//
				Set<String> indirectContactIds = new HashSet<String>();
				for (String suspectInfectedId : directContactIds) {
					Set<String> subIndirectContactIds = getIndirectContactIds(suspectInfectedId, infectionDate, infection.getInfectionUserId(), directContactIds);
					indirectContactIds.addAll(subIndirectContactIds);
				}
				infectionRegistrationReply = new InfectionRegistrationReply(infection.getInfectionUserId(), infection.getInfectionDate(), infection.getInfectionReporterId(), directContactIds.toString(), indirectContactIds.toString(), "Infection reported successfully", 400);
				logger.debug("Step 3, indirect contacts for contact [" + infection.getInfectionUserId() + "] : " + indirectContactIds);

				//--------------- Step 4: Update Account Table for all direct contacts ------------//
				for (String accountId : directContactIds) {
					incrementNumberOfContact(accountId, "numberOfDirectInfectedContact");
				}
				logger.debug("Step 4, direct contacts updated successfully!");
				
				//--------------- Step 5: Update Account Table for all direct contacts ------------//
				for (String accountId : indirectContactIds) {
					incrementNumberOfContact(accountId, "numberOfIndirectInfectedContact");
				}
				logger.debug("Step 5, indirect contacts updated successfully!");
			}			
		} catch (Exception e) {
			logger.error("Error while reporting infection for user having id[" + infection.getInfectionUserId()+"] ", e);
		} finally {
			closeAccountTable();
			closeContactTable();
		}
		return infectionRegistrationReply;
	}
	
	private Set<String> getDirectContactIds(String infectedId, LocalDate infectionDate) throws IOException {
		if (infectedId == null || infectionDate == null) return null; 
		
		Set<String> contactIds = new HashSet<String>();
		
		//Scan Contact Table by filtering on row keys that contain the infected id (each id is prefixed by §)				
		Scan scan = new Scan();
		Filter filter = new RowFilter(CompareOp.EQUAL, new SubstringComparator("§"+infectedId+"§"));
		scan.setFilter(filter);
		ResultScanner resultScanner = contactTable.getScanner(scan);
		
		for (Result rs : resultScanner) {
			//Filter on row keys for the contacts that happened in the last 21 days
			String contactRowKey = new String(rs.getRow());
			try {
				String contactDateString = contactRowKey.substring(0, contactRowKey.indexOf('§'));
				LocalDate contactDate = Instant.ofEpochSecond(Long.parseLong(contactDateString)).atZone(ZoneId.systemDefault()).toLocalDate();
				if (ChronoUnit.DAYS.between(contactDate, infectionDate) < 22) {
					List<String> contactList = Arrays.asList(contactRowKey.substring(contactRowKey.indexOf('§')+1).split("§"));
					for (String contactId : contactList) {
						if (contactId.length() > 0 && !contactId.equals(infectedId)) {
							contactIds.add(contactId);
						}						
					}
				}
			}catch(Exception e) {
				//Nothing to do, just ignore this contact
			}
		}
		return contactIds;
	}
	
	private Set<String> getIndirectContactIds(String suspectInfectedId, LocalDate infectionDate, String infectedId, Set<String> directContacts) throws IOException {
		if (suspectInfectedId == null || infectionDate == null || infectedId == null || directContacts == null || directContacts.size() == 0) return null; 
		
		Set<String> contactIds = new HashSet<String>();
		
		//Scan Contact Table by filtering on row keys that contain the infected id (each id is prefixed by §)				
		Scan scan = new Scan();
		Filter filter = new RowFilter(CompareOp.EQUAL, new SubstringComparator("§"+suspectInfectedId+"§"));
		scan.setFilter(filter);
		ResultScanner resultScanner = contactTable.getScanner(scan);
		
		for (Result rs : resultScanner) {
			//Filter on row keys for the contacts that happened in the last 21 days
			String contactRowKey = new String(rs.getRow());
			try {
				String contactDateString = contactRowKey.substring(0, contactRowKey.indexOf('§'));
				LocalDate contactDate = Instant.ofEpochSecond(Long.parseLong(contactDateString)).atZone(ZoneId.systemDefault()).toLocalDate();
				if (ChronoUnit.DAYS.between(contactDate, infectionDate) < 22) {
					List<String> contactList = Arrays.asList(contactRowKey.substring(contactRowKey.indexOf('§')+1).split("§"));
					for (String contactId : contactList) {
						if (contactId.length() > 0 && !contactId.equals(suspectInfectedId) && !contactId.equals(infectedId) && !directContacts.contains(contactId)) {
							contactIds.add(contactId);
						}
					}
				}
			}catch(Exception e) {
				//Nothing to do, just ignore this contact
			}
		}		
		return contactIds;
	}
	
	public void incrementNumberOfContact(String accountId, String columnName) throws IOException {
		// instantiate Get class : need to know if the contact is already stored
		Get g = new Get(Bytes.toBytes(accountId));

		// get the Result object
		Result result = accountTable.get(g);			
		if (result != null && !result.isEmpty()) {
			// read values from Result class object
			Integer numberOfContacts = Bytes.toInt(result.getValue(Bytes.toBytes("contact"), Bytes.toBytes(columnName)));

			// instantiate Put class
			Put p = new Put(Bytes.toBytes(accountId));

			// add values using addColumn() method
			p.addColumn(Bytes.toBytes("contact"), Bytes.toBytes(columnName), Bytes.toBytes(numberOfContacts+1));

			// save the put Instance to the HTable.
			accountTable.put(p);
		}		
	}
	
}
