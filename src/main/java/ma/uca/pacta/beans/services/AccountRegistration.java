package ma.uca.pacta.beans.services;

import java.io.IOException;

import org.apache.log4j.Logger;

import ma.uca.pacta.beans.Account;
import ma.uca.pacta.beans.AccountRegistrationReply;
import ma.uca.pacta.dao.AccountDAO;

public class AccountRegistration {
	final static Logger logger = Logger.getLogger(AccountRegistration.class);
	private static AccountRegistration accountRegistrationInstance = null;
	
	//Singleton : one instance is sufficient to do the job
    public static AccountRegistration getInstance() {
        if(accountRegistrationInstance == null) {
        	accountRegistrationInstance = new AccountRegistration();
        	return accountRegistrationInstance;
        } else {
        	return accountRegistrationInstance;
        }
    }
	    
	public AccountRegistrationReply registerAccount(Account account) {
		logger.debug("Start registering new account: " + account.toString() + "...");
    	try {
			return AccountDAO.getInstance().createAccount(account);
		} catch (IOException e) {
			logger.error("Error while registering new account having id[" + account.getId() + "] ", e);
		}
    	return null;
    	
    }

    public Account readAccount(String accountId) {
    	logger.debug("Start looking for account having id[" + accountId + "]...");
    	try {
			return AccountDAO.getInstance().findAccount(accountId);
		} catch (IOException e) {
			logger.error("Error while looking for account having id[" + accountId + "] ", e);
		}
    	return null;
    }
    
}
