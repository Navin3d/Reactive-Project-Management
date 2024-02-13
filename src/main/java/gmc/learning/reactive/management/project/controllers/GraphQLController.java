package gmc.learning.reactive.management.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.learning.reactive.management.project.models.ProjectGraphModel;
import gmc.learning.reactive.management.project.services.GraphQLService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(path = "/graphql")
@RestController
public class GraphQLController {
	
	@Autowired
	private GraphQLService graphService;
	
	@GetMapping
	private Flux<ProjectGraphModel> projects() {
		return graphService.projects(1, 10);
	}
	
	@GetMapping(path = "/{projectId}")
	private Mono<ProjectGraphModel> project(@PathVariable String projectId) {
		return graphService.project(projectId);
	}
	
//	@QueryMapping
//	private Mono<DeveloperGraphModel> developer(String developerId) {
//		return graphService.developer(developerId);
//	}

}
