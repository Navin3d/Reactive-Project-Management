package gmc.learning.reactive.management.project.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Update;

import gmc.learning.reactive.management.project.entities.ProjectEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectDao extends ReactiveMongoRepository<ProjectEntity, String> {
	
	public Flux<ProjectEntity> findByStatus(Boolean status, Pageable pageable);

	@Query("{'_id': ?0}")
	@Update("{ '$set': { 'status': ?1 } }")
	public Mono<Void> updateStatus(String id, Boolean status);
	
	@Query("{'_id': ?0}")
	@Update("{ '$addToSet': { 'tasks': ?1 } }")
	public Mono<Void> pushToTasks(String id, String taskId);
	
	@Query("{'_id': ?0}")
	@Update("{ '$addToSet': { 'requestedDevelopers': ?1 } }")
	public Mono<Void> pushToJoinRequests(String id, String userId);
	
}
