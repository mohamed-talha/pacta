package ma.uca.pacta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ma.uca.pacta.beans.Infection;
import ma.uca.pacta.beans.InfectionRegistrationReply;
import ma.uca.pacta.beans.services.InfectionRegistration;

@Controller
public class InfectionController {

	  @RequestMapping(method = RequestMethod.POST, value="/infection/reportInfection")
	  @ResponseBody
	  public InfectionRegistrationReply reportInfection(@RequestBody Infection infection) {
		  return InfectionRegistration.getInstance().registerInfection(infection);
	  }
}
