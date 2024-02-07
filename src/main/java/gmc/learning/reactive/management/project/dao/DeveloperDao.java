package gmc.learning.reactive.management.project.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import reactor.core.publisher.Mono;

public interface DeveloperDao extends ReactiveMongoRepository<DeveloperEntity, String> {

	public Mono<DeveloperEntity> findByEmail(String email);
	
}
