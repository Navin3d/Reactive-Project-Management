package gmc.learning.reactive.management.project.services;

import gmc.learning.reactive.management.project.entities.TaskEntity;
import reactor.core.publisher.Mono;

public interface TaskService {
	
	public Mono<TaskEntity> saveTask(String id, TaskEntity task);
	public Mono<TaskEntity> commentTask(String id, String comment);
	public Mono<TaskEntity> updateStatus(String id, Boolean sttatus);

}
