package gmc.learning.reactive.management.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.dao.TaskDao;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import gmc.learning.reactive.management.project.services.TaskService;
import reactor.core.publisher.Mono;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskDao taskDao;

	@Override
	public Mono<TaskEntity> saveTask(TaskEntity task) {
		return taskDao.save(task);
	}

	@Override
	public Mono<Void> commentTask(String id, String comment) {
		return taskDao.pushToComments(id, comment);
	}

	@Override
	public Mono<Void> updateStatus(String id, Boolean sttatus) {
		return taskDao.updateStatus(id, sttatus);
	}

}
