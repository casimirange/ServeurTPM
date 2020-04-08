package com.example.demo.Controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin
public class UserController {	
	@RequestMapping(value = "/api/user")
	public Principal user(Principal user) {
		return user;
	}

}
