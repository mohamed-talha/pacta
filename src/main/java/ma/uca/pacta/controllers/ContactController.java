package ma.uca.pacta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import ma.uca.pacta.beans.Contact;
import ma.uca.pacta.beans.ContactRegistrationReply;
import ma.uca.pacta.beans.services.ContactRegistration;

@Controller
public class ContactController {

	  @RequestMapping(method = RequestMethod.POST, value="/contact/newContact")
	  @ResponseBody
	  public ContactRegistrationReply createAccount(@RequestBody Contact contact) {
		  return ContactRegistration.getInstance().registerContact(contact);
	  }
}
