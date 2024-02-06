package gmc.learning.reactive.management.project.services;

import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import reactor.core.publisher.Mono;

public interface DeveloperService {

	public Mono<DeveloperEntity> findOne(String uniqueId);
	
	public Mono<DeveloperEntity> save(DeveloperEntity developerEntity);
	
}
