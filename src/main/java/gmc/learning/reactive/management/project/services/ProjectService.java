package gmc.learning.reactive.management.project.services;

import gmc.learning.reactive.management.project.entities.ProjectEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
	
	public Mono<ProjectEntity> findOne(String id);
	
	public Flux<ProjectEntity> findMany(Integer page, Integer size, Boolean status);

}
