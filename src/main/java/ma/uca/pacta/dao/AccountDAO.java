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

import ma.uca.pacta.beans.Account;
import ma.uca.pacta.beans.AccountRegistrationReply;
import ma.uca.pacta.utils.ZookeeperConnection;

public class AccountDAO {
	final static Logger logger = Logger.getLogger(AccountDAO.class);
	
	Configuration config;
	Connection connection;
	Table accountTable;

	private static AccountDAO accountDAOInstance = null;
	
	// Singleton : one instance is sufficient to do the job
	public static AccountDAO getInstance() {
		if (accountDAOInstance == null) {
			accountDAOInstance = new AccountDAO();
			return accountDAOInstance;
		} else {
			return accountDAOInstance;
		}
	}	

	private AccountDAO() {
		// Configuration
		config = ZookeeperConnection.config;
		
		//Connection
		try {
			connection = ConnectionFactory.createConnection(config);
		} catch (IOException e) {
			logger.error("Failed to open connection for AccountDAO!", e);
		}
	}
	
	private void openAccountTable() {
		try {
			if (accountTable == null) {
				accountTable = connection.getTable(TableName.valueOf("account"));					
			}			
		} catch (IOException e) {
			logger.error("Failed to open account table!", e);
		}
	}

	private void closeAccountTable() {
		try {
			if (accountTable != null) {
				accountTable.close();
			}
		} catch (IOException e) {
			logger.error("Failed to close account table!", e);
		}
	}

	// Create new account
	public AccountRegistrationReply createAccount(Account account) throws IOException {
		if (account == null) return null;
		logger.debug("Start creating new account: " + account.toString() + "...");
		AccountRegistrationReply accountRegistrationReply = null;
		
		try {
			//Connect to Account Table
			openAccountTable();

			// instantiate Put class
			Put p = new Put(Bytes.toBytes(account.getId()));

			// add values using addColumn() method
			p.addColumn(Bytes.toBytes("contact"), Bytes.toBytes("numberOfDirectInfectedContact"),Bytes.toBytes(account.getNumberOfDirectInfectedContact()));
			p.addColumn(Bytes.toBytes("contact"), Bytes.toBytes("numberOfIndirectInfectedContact"),Bytes.toBytes(account.getNumberOfIndirectInfectedContact()));
			p.addColumn(Bytes.toBytes("infection"), Bytes.toBytes("isInfected"), Bytes.toBytes(account.getIsInfected()));
			p.addColumn(Bytes.toBytes("infection"), Bytes.toBytes("infectedOn"), Bytes.toBytes(account.getInfectedOn()));
			p.addColumn(Bytes.toBytes("infection"), Bytes.toBytes("infectionDeclaredBy"),Bytes.toBytes(account.getInfectionDeclaredBy()));

			// save the put Instance to the HTable.
			accountTable.put(p);

			//Success			
			accountRegistrationReply = new AccountRegistrationReply(account.getId(), account.getNumberOfDirectInfectedContact(), account.getNumberOfIndirectInfectedContact(), account.getIsInfected(), "New account registred successfully", 200);
			logger.debug("New account registred successfully!");
		} catch (Exception e) {
			logger.error("Error while creating account having id[" + account.getId()+"] ", e);
		} finally {
			closeAccountTable();
		}
		return accountRegistrationReply;
	}

	// Find account by id
	public Account findAccount(String accountId) throws IOException {
		if (accountId == null)return null;
		logger.debug("Start looking for account having id[" + accountId+"]...");
		Account account = null;

		try {
			//Connect to Account Table
			openAccountTable();

			// instantiate Get class
			Get g = new Get(Bytes.toBytes(accountId));

			// get the Result object
			Result result = accountTable.get(g);

			// read values from Result class object
			byte[] numberOfDirectInfectedContact = result.getValue(Bytes.toBytes("contact"), Bytes.toBytes("numberOfDirectInfectedContact"));
			byte[] numberOfIndirectInfectedContact = result.getValue(Bytes.toBytes("contact"), Bytes.toBytes("numberOfIndirectInfectedContact"));
			byte[] isInfected = result.getValue(Bytes.toBytes("infection"), Bytes.toBytes("isInfected"));
			byte[] infectedOn = result.getValue(Bytes.toBytes("infection"), Bytes.toBytes("infectedOn"));
			byte[] infectionDeclaredBy = result.getValue(Bytes.toBytes("infection"), Bytes.toBytes("infectionDeclaredBy"));

			//Success			
			account = new Account(accountId, Bytes.toInt(numberOfDirectInfectedContact), Bytes.toInt(numberOfIndirectInfectedContact), Bytes.toBoolean(isInfected), Bytes.toLong(infectedOn), Bytes.toString(infectionDeclaredBy));
			logger.debug("account found: " + account.toString());
		} catch(Exception e) {
			logger.error("Error while looking for account having id[" + accountId+"] ", e);
		} finally {
			closeAccountTable();
		}
		return account;
	}
}
