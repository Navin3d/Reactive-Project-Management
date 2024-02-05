package gmc.learning.reactive.management.project.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import gmc.learning.reactive.management.project.entities.ProjectEntity;
import reactor.core.publisher.Flux;

public interface ProjectDao extends ReactiveMongoRepository<ProjectEntity, String> {

	public Flux<ProjectEntity> findByStatus(Boolean status, Pageable pageable);
	
}
