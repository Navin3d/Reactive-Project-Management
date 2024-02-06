package gmc.learning.reactive.management.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.learning.reactive.management.project.entities.TaskEntity;
import gmc.learning.reactive.management.project.services.TaskService;
import reactor.core.publisher.Mono;

@RequestMapping(path = "/task")
@RestController
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping(path = "/{taskId}/comment/{comment}")
	private Mono<TaskEntity> commentOnTask(@PathVariable String taskId, @PathVariable String comment) {
		return taskService.commentTask(taskId, comment);
	}
	
	@GetMapping(path = "/{taskId}/status/{status}")
	private Mono<TaskEntity> changeTaskStatus(@PathVariable String taskId, @PathVariable Boolean status) {
		return taskService.updateStatus(taskId, status);
	}
	
	@PostMapping(path = "/{projectId}")
	private Mono<TaskEntity> task(@PathVariable String projectId, @RequestBody TaskEntity taskToAssign) {
		return taskService.saveTask(projectId, taskToAssign);
	}

}
