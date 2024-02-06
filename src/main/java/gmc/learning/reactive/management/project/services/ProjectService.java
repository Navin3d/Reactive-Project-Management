package gmc.learning.reactive.management.project.services;

import gmc.learning.reactive.management.project.entities.ProjectEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProjectService {
	
	public Mono<ProjectEntity> findOne(String id);	
	public Flux<ProjectEntity> findMany(Integer page, Integer size, Boolean status);
	
	public Mono<ProjectEntity> save(ProjectEntity project);	
	public Mono<ProjectEntity> switchProjectStatus(String projectId, Boolean status);
	
	public Mono<ProjectEntity> requestJoin(String projectId, String userId);
	public Mono<ProjectEntity> acceptJoinRequest(String projectId, String userId);
	public Mono<ProjectEntity> rejectJoinRequest(String projectId, String userId);
	
}
