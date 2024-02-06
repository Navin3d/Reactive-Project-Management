package gmc.learning.reactive.management.project.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import gmc.learning.reactive.management.project.entities.TaskEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskDao extends ReactiveMongoRepository<TaskEntity, String> {
	
	public Flux<TaskEntity> findByAssignedTo(String userId);
	
	public Flux<TaskEntity> findByAssignedToAndStatus(String userId, Boolean status);
	
	@Query("{'_id': ?0}")
	public Mono<TaskEntity> pushToComments(String id, String value);
	
	@Query("{'_id': :id}")
	public Mono<TaskEntity> updateField(String id, Boolean status);
	
}
