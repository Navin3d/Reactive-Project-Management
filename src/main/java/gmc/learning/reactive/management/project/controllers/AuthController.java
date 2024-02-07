package gmc.learning.reactive.management.project.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.learning.reactive.management.project.models.DeveloperModel;
import gmc.learning.reactive.management.project.models.LoginModel;
import gmc.learning.reactive.management.project.services.AuthService;
import gmc.learning.reactive.management.project.utils.JwtTokenProvider;
import reactor.core.publisher.Mono;

@RequestMapping(path = "/auth")
@RestController
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private JwtTokenProvider tokenProvider;
	
	@Autowired
	private ReactiveAuthenticationManager authenticationManager;
	
	@PostMapping("/login")
    public Mono<ResponseEntity<Map<String, String>>> login(@RequestBody Mono<LoginModel> authRequest) {
        return authRequest
                .flatMap(login -> this.authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(
                                login.getUserName(), login.getPassword()))
                        .map(this.tokenProvider::createToken))
                .map(jwt -> {
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
                    Map<String, String> tokenBody = Map.of("access_token", jwt);
                    return new ResponseEntity<Map<String, String>>(tokenBody, httpHeaders, HttpStatus.OK);
                });
    }
	
	@PostMapping
	private Mono<DeveloperModel> registerUser(@RequestBody DeveloperModel newUser) {
		return authService.registerUser(newUser);
	}
	
	@PutMapping
	private Mono<DeveloperModel> completeProfile(@RequestBody DeveloperModel newUser) {
		return authService.completeProfile(newUser);
	}

}
