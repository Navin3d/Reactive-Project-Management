package gmc.learning.reactive.management.project.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import gmc.learning.reactive.management.project.entities.ProjectEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectDao extends ReactiveMongoRepository<ProjectEntity, String> {
	
	public Flux<ProjectEntity> findByStatus(Boolean status, Pageable pageable);

	@Query("{'_id': ?0}")
	public Mono<ProjectEntity> updateField(String id, Boolean value);
	
	@Query("{'_id': ?0}")
	public Mono<ProjectEntity> pushToTasks(String id, String value);
	
}
