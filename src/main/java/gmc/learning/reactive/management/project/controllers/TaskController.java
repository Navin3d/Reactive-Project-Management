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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(path = "/task")
@RestController
public class TaskController {
	
	@Autowired
	private TaskService taskService;
	
	@GetMapping(path = "/{taskId}/comment/{comment}")
	private Mono<Void> commentOnTask(@PathVariable String taskId, @PathVariable String comment) {
		return taskService.commentTask(taskId, comment);
	}
	
	@GetMapping(path = "/{taskId}/status/{status}")
	private Mono<Void> changeTaskStatus(@PathVariable String taskId, @PathVariable Boolean status) {
		return taskService.updateStatus(taskId, status);
	}
	
	@PostMapping
	private Mono<TaskEntity> task(@RequestBody Mono<TaskEntity> taskToAssign) {
		return taskToAssign.flatMap(taskService::saveTask) ;
	}
	
	@PostMapping(path = "/save-many")
	private Mono<Boolean> taskMany(@RequestBody Flux<TaskEntity> tasksToAssign) {
		return tasksToAssign.collectList().flatMap(taskService::saveAll);
	}

}
