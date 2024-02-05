package gmc.learning.reactive.management.project.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import gmc.learning.reactive.management.project.entities.TaskEntity;
import reactor.core.publisher.Flux;

public interface TaskDao extends ReactiveMongoRepository<TaskEntity, String> {
	
	@Query("{assignedTo: { id: ?0 }}")
	public Flux<TaskEntity> findByUser(String userId);
	
	@Query("{assignedTo: { id: ?0 }, status: ?1}")
	public Flux<TaskEntity> findByUserAndStatus(String userId, Boolean status);
	
}
