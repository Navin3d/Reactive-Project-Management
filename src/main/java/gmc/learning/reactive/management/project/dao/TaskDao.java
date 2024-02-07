package gmc.learning.reactive.management.project.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;

import gmc.learning.reactive.management.project.entities.TaskEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TaskDao extends ReactiveMongoRepository<TaskEntity, String> {
	
	public Flux<TaskEntity> findByAssignedTo(String userId);
	
	public Flux<TaskEntity> findByAssignedToAndStatus(String userId, Boolean status);
	
	@Query("{'_id': ?0}")
	@Update("{ '$addToSet': { 'comments': ?1 } }")
	public Mono<Void> pushToComments(String id, String comment);
	
	@Query("{'_id': ?0}")
	@Update("{ '$set': { 'status': ?1 } }")
	public Mono<Void> updateField(String id, Boolean status);
	
}
