package gmc.learning.reactive.management.project.services;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

import gmc.learning.reactive.management.project.models.DeveloperModel;
import reactor.core.publisher.Mono;

public interface AuthService extends ReactiveUserDetailsService {
	
	public Mono<DeveloperModel> registerUser(DeveloperModel developerModel);	
	public Mono<DeveloperModel> completeProfile(DeveloperModel developerModel);

}
