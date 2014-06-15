package soa.common.controller;

import java.security.Principal;

import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import soa.common.security.LoggedUser;

@RepositoryRestController
@RequestMapping("/api")
public class Controller {

	@RequestMapping(value="/login", method=RequestMethod.GET)
	@ResponseBody
	public Resource<LoggedUser> login(Principal principal){
		return new Resource<LoggedUser>((LoggedUser)((Authentication) principal).getPrincipal());
	}
}
