package ma.uca.pacta.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ma.uca.pacta.beans.Account;
import ma.uca.pacta.beans.AccountRegistrationReply;
import ma.uca.pacta.beans.services.AccountRegistration;

@Controller
public class AccountController {

	  @RequestMapping(method = RequestMethod.GET, value="/account/getAccount")
	  @ResponseBody
	  public Account getAccount(@RequestParam("accountId") String accountId) {
		  return AccountRegistration.getInstance().readAccount(accountId);
	  }

	  @RequestMapping(method = RequestMethod.POST, value="/account/createAccount")
	  @ResponseBody
	  public AccountRegistrationReply createAccount(@RequestBody Account account) {
		  return AccountRegistration.getInstance().registerAccount(account);
	  }

}
