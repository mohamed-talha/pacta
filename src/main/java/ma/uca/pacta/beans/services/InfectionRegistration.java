package ma.uca.pacta.beans.services;

import java.io.IOException;

import org.apache.log4j.Logger;

import ma.uca.pacta.beans.Infection;
import ma.uca.pacta.beans.InfectionRegistrationReply;
import ma.uca.pacta.dao.InfectionDAO;

public class InfectionRegistration {
	final static Logger logger = Logger.getLogger(InfectionRegistration.class);
	private static InfectionRegistration infectionRegistrationInstance = null;
	
	//Singleton : one instance is sufficient to do the job
    public static InfectionRegistration getInstance() {
        if(infectionRegistrationInstance == null) {
        	infectionRegistrationInstance = new InfectionRegistration();
        	return infectionRegistrationInstance;
        } else {
        	return infectionRegistrationInstance;
        }
    }
	    
	public InfectionRegistrationReply registerInfection(Infection infection) {
    	logger.debug("Start registering new infection: " + infection.toString() + "...");
    	try {
			return InfectionDAO.getInstance().reportInfection(infection);
		} catch (IOException e) {
			logger.error("Error while registering new infection for user having id [" + infection.getInfectionUserId() + "] ", e);
		}
    	return null;
    	
    }
}
