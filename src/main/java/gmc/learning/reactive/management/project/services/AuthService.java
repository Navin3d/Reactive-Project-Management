package gmc.learning.reactive.management.project.services;

import org.springframework.security.core.userdetails.ReactiveUserDetailsService;

import gmc.learning.reactive.management.project.models.DeveloperModel;
import reactor.core.publisher.Mono;

public interface AuthService extends ReactiveUserDetailsService {
	
	public static String OTP_SEPERATOR = "@NAVIN@";
	
	public Mono<Boolean> isM2FEnabled(String userId);
	public Mono<String> toggleM2F(String userId, Boolean status);
	
	public Mono<DeveloperModel> registerUser(DeveloperModel developerModel);	
	public Mono<DeveloperModel> completeProfile(DeveloperModel developerModel);

}
