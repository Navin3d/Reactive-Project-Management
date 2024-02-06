package gmc.learning.reactive.management.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.learning.reactive.management.project.models.DeveloperModel;
import gmc.learning.reactive.management.project.services.AuthService;
import reactor.core.publisher.Mono;

@RequestMapping(path = "/auth")
@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@PostMapping
	private Mono<DeveloperModel> registerUser(@RequestBody DeveloperModel newUser) {
		return authService.registerUser(newUser);
	}
	
	@PutMapping
	private Mono<DeveloperModel> completeProfile(@RequestBody DeveloperModel newUser) {
		return authService.completeProfile(newUser);
	}

}
