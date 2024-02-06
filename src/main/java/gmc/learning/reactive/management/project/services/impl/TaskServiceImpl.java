package gmc.learning.reactive.management.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.dao.ProjectDao;
import gmc.learning.reactive.management.project.dao.TaskDao;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import gmc.learning.reactive.management.project.services.TaskService;
import reactor.core.publisher.Mono;

@Service
public class TaskServiceImpl implements TaskService {
	
	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private ProjectDao projectDao;
	
	@Override
	public Mono<TaskEntity> saveTask(String projectId, TaskEntity task) {
		return taskDao.save(task)
				.map(savedTask -> {
					projectDao.findById(projectId)
					.flatMap(foundProject -> {
						foundProject.getTasks().add(savedTask.getId());
						return projectDao.save(foundProject);
					});
					return savedTask;
				});
	}

	@Override
	public Mono<TaskEntity> commentTask(String id, String comment) {
		return taskDao.pushToComments(id, comment);
	}

	@Override
	public Mono<TaskEntity> updateStatus(String id, Boolean sttatus) {
		return taskDao.updateField(id, sttatus);
	}

}
