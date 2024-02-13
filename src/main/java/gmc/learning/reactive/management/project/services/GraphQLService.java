package gmc.learning.reactive.management.project.services;

import gmc.learning.reactive.management.project.models.DeveloperGraphModel;
import gmc.learning.reactive.management.project.models.ProjectGraphModel;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface GraphQLService {
	
	public Flux<ProjectGraphModel> projects(Integer page, Integer size);
	
	public Mono<ProjectGraphModel> project(String id);
	
	public Mono<DeveloperGraphModel> developer(String id);

}
