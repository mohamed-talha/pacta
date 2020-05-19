package ma.uca.pacta.dao;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.log4j.Logger;

import ma.uca.pacta.beans.Contact;
import ma.uca.pacta.beans.ContactRegistrationReply;
import ma.uca.pacta.utils.ZookeeperConnection;

public class ContactDAO {
	final static Logger logger = Logger.getLogger(ContactDAO.class);
	Configuration config;
	Connection connection;
	Table contactTable;

	private static ContactDAO contactDAOInstance = null;
	
	// Singleton : one instance is sufficient to do the job
	public static ContactDAO getInstance() {
		if (contactDAOInstance == null) {
			contactDAOInstance = new ContactDAO();
			return contactDAOInstance;
		} else {
			return contactDAOInstance;
		}
	}

	private ContactDAO() {
		// Configuration
		config = ZookeeperConnection.config;
		
		//Connection
		try {
			connection = ConnectionFactory.createConnection(config);
		} catch (IOException e) {
			logger.error("Failed to open connection for ContactDAO!", e);
		}
	}
	
	private void openContactTable() {
		try {
			if (contactTable == null) {
				contactTable = connection.getTable(TableName.valueOf("contact"));					
			}			
		} catch (IOException e) {
			logger.error("Failed to open contact table!", e);
		}
	}

	private void closeContactTable() {
		try {
			if (contactTable != null) {
				contactTable.close();
			}
		} catch (IOException e) {
			logger.error("Failed to close contact table!", e);
		}
	}

	// Add new contact
	public ContactRegistrationReply createContact(Contact contact) throws IOException {
		if (contact == null) return null;
		logger.debug("Start creating new contact: " + contact.toString() + "...");
		ContactRegistrationReply contactRegistrationReply = null;
		
		try {
			//Connect to Contact Table
			openContactTable();
			
			// instantiate Get class : need to know if the contact is already stored
			Get g = new Get(Bytes.toBytes(contact.getId()));

			// get the Result object
			Result result = contactTable.get(g);
			
			// get the sender
			String sender;
			if (result == null || result.isEmpty()) {//new contact
				sender = contact.getSender();
			} else {//contact is already stored
				sender = Bytes.toString(result.getValue(Bytes.toBytes("sender"), Bytes.toBytes("ids"))) + "§" + contact.getSender();
			}

			if (sender != null) {
				// instantiate Put class
				Put p = new Put(Bytes.toBytes(contact.getId()));

				// add values using addComlumn() method
				p.addColumn(Bytes.toBytes("sender"), Bytes.toBytes("ids"),Bytes.toBytes(sender));

				// save the put Instance to the HTable.
				contactTable.put(p);

				//Success
				contactRegistrationReply = new ContactRegistrationReply(contact.getId(), sender, "New contact registred successfully", 200);
				logger.debug("New contact registred successfully!");
			}
		} catch (Exception e) {
			logger.error("Error while creating contact having id[" + contact.getId()+"] ", e);
		} finally {
			closeContactTable();
		}
		return contactRegistrationReply;
	}
}
