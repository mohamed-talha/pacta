package ma.uca.pacta.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ma.uca.pacta.beans.Infection;
import ma.uca.pacta.beans.InfectionRegistrationReply;
import ma.uca.pacta.beans.services.InfectionRegistration;

@CrossOrigin
@RestController
@RequestMapping("/infection")
public class InfectionController {

	  @RequestMapping(method = RequestMethod.POST, value="/reportInfection")
	  @ResponseBody
	  public InfectionRegistrationReply reportInfection(@RequestBody Infection infection) {
		  return InfectionRegistration.getInstance().registerInfection(infection);
	  }
}
