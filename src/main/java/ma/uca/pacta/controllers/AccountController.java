package ma.uca.pacta.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ma.uca.pacta.beans.Account;
import ma.uca.pacta.beans.AccountRegistrationReply;
import ma.uca.pacta.beans.services.AccountRegistration;

@CrossOrigin
@RestController
@RequestMapping("/account")
public class AccountController {

	  @RequestMapping(method = RequestMethod.GET, value="/getAccount")
	  @ResponseBody
	  public Account getAccount(@RequestParam("accountId") String accountId) {
		  return AccountRegistration.getInstance().readAccount(accountId);
	  }

	  @RequestMapping(method = RequestMethod.POST, value="/createAccount")
	  @ResponseBody
	  public AccountRegistrationReply createAccount(@RequestBody Account account) {
		  return AccountRegistration.getInstance().registerAccount(account);
	  }

}
