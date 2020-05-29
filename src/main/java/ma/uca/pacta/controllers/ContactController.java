package ma.uca.pacta.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ma.uca.pacta.beans.Contact;
import ma.uca.pacta.beans.ContactRegistrationReply;
import ma.uca.pacta.beans.services.ContactRegistration;

@CrossOrigin
@RestController
@RequestMapping("/contact")
public class ContactController {

	  @RequestMapping(method = RequestMethod.POST, value="/newContact")
	  @ResponseBody
	  public ContactRegistrationReply createAccount(@RequestBody Contact contact) {
		  return ContactRegistration.getInstance().registerContact(contact);
	  }
}
