package gmc.learning.reactive.management.project.services.impl;

import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.models.DeveloperGraphModel;
import gmc.learning.reactive.management.project.models.ProjectGraphModel;
import gmc.learning.reactive.management.project.services.GraphQLService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GraphQLServiceImpl implements GraphQLService {

	@Override
	public Flux<ProjectGraphModel> projects(Integer page, Integer size) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ProjectGraphModel> project(Mono<String> id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<DeveloperGraphModel> developer(Mono<String> id) {
		// TODO Auto-generated method stub
		return null;
	}

}
