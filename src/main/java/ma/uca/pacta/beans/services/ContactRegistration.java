package ma.uca.pacta.beans.services;

import java.io.IOException;

import org.apache.log4j.Logger;

import ma.uca.pacta.beans.Contact;
import ma.uca.pacta.beans.ContactRegistrationReply;
import ma.uca.pacta.dao.ContactDAO;

public class ContactRegistration {
	final static Logger logger = Logger.getLogger(ContactRegistration.class);
	private static ContactRegistration contactRegistrationInstance = null;
	
	//Singleton : one instance is sufficient to do the job
    public static ContactRegistration getInstance() {
        if(contactRegistrationInstance == null) {
        	contactRegistrationInstance = new ContactRegistration();
        	return contactRegistrationInstance;
        } else {
        	return contactRegistrationInstance;
        }
    }
	    
	public ContactRegistrationReply registerContact(Contact contact) {
    	logger.debug("Start registering new contact: " + contact.toString() + "...");
    	try {
			return ContactDAO.getInstance().createContact(contact);
		} catch (IOException e) {
			logger.error("Error while registering new contact having id[" + contact.getId() + "] ", e);
		}
    	return null;
    	
    }
}
