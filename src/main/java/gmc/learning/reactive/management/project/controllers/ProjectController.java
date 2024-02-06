package gmc.learning.reactive.management.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.services.ProjectService;
import reactor.core.publisher.Mono;

@RequestMapping(path = "/project")
@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@GetMapping(path = "/{projectId}/request/{userId}")
	private Mono<ProjectEntity> requestJoin(@PathVariable String projectId, @PathVariable String userId) {
		return projectService.requestJoin(projectId, userId);
	}
	
	@GetMapping(path = "/{projectId}/accept/{userId}")
	private Mono<ProjectEntity> acceptRequest(@PathVariable String projectId, @PathVariable String userId) {
		return projectService.acceptJoinRequest(projectId, userId);
	}
	
	@GetMapping(path = "/{projectId}/reject/{userId}")
	private Mono<ProjectEntity> rejectRequest(@PathVariable String projectId, @PathVariable String userId) {
		return projectService.rejectJoinRequest(projectId, userId);
	}
	
	@GetMapping(path = "/{projectId}/{status}")
	private Mono<ProjectEntity> changeProjectStatus(@PathVariable String projectId, @PathVariable Boolean status) {
		return projectService.switchProjectStatus(projectId, status);
	}
	
	@PostMapping
	private Mono<ProjectEntity> save(@RequestBody ProjectEntity newProject) {
		return projectService.save(newProject);
	}
	
}
