package gmc.learning.reactive.management.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.learning.reactive.management.project.models.ProjectGraphModel;
import gmc.learning.reactive.management.project.services.GraphQLService;
import reactor.core.publisher.Flux;

@RequestMapping(path = "/graphql")
@RestController
public class GraphQLController {
	
	@Autowired
	private GraphQLService graphService;
	
	@QueryMapping
	private Flux<ProjectGraphModel> projects(Integer page, Integer size) {
		return graphService.projects(page, size);
	}

}
