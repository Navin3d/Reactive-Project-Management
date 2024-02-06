package gmc.learning.reactive.management.project.services;

import gmc.learning.reactive.management.project.models.DeveloperModel;
import reactor.core.publisher.Mono;

public interface AuthService {
	
	public Mono<DeveloperModel> registerUser(DeveloperModel developerModel);	
	public Mono<DeveloperModel> completeProfile(DeveloperModel developerModel);

}
