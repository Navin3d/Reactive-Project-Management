package gmc.learning.reactive.management.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import gmc.learning.reactive.management.project.services.ProjectService;
import reactor.core.publisher.Mono;

@RequestMapping(path = "/project")
@RestController
public class ProjectController {
	
	@Autowired
	private ProjectService projectService;
	
	@PostMapping
	private Mono<ProjectEntity> createProject(@RequestBody ProjectEntity newProject) {
		return projectService.save(newProject);
	}

	@PostMapping(path = "/task")
	private Mono<ProjectEntity> assignTask(@RequestBody TaskEntity taskToAssign) {
		return projectService.addTask(taskToAssign);
	}
	
	@PutMapping(path = "/task")
	private Mono<ProjectEntity> updateTask(@RequestBody TaskEntity taskToUpdate) {
		return projectService.updateTask(taskToUpdate);
	}
	
}
