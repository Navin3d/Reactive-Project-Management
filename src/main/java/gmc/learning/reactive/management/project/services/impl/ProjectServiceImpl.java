package gmc.learning.reactive.management.project.services.impl;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.dao.ProjectDao;
import gmc.learning.reactive.management.project.dao.TaskDao;
import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import gmc.learning.reactive.management.project.services.DeveloperService;
import gmc.learning.reactive.management.project.services.ProjectService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDao projectDao;
	
	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private DeveloperService developerService;

	@Override
	public Mono<ProjectEntity> findOne(String id) {
		return projectDao.findById(id);
	}

	@Override
	public Flux<ProjectEntity> findMany(Integer page, Integer size, Boolean status) {
		Pageable pageConfig = PageRequest.of(page, size, Sort.by("tittle"));
		return projectDao.findByStatus(status, pageConfig);
	}
	
//	private ProjectEntity 

	@Override
	public Mono<ProjectEntity> save(ProjectEntity newProject) {
		return projectDao.save(newProject);
	}
	
	private CompletableFuture<ProjectEntity> saveAndUpdateTask(Mono<ProjectEntity> belongsTo, TaskEntity task) {
		Function<Mono<TaskEntity>, ProjectEntity> addTaskToProjectAndSave = savedNewTask -> {
			AtomicReference<ProjectEntity> projectRef = new AtomicReference<>();
			savedNewTask.subscribe(savedTaskSubscriber -> {
				belongsTo.subscribe(project -> {
					project.getTasks().add(savedTaskSubscriber);
					projectDao.save(project).subscribe(pr -> {
						projectRef.lazySet(pr);
					});
				});
			});
			return projectRef.get();
		};
		Supplier<ProjectEntity> save = () -> {
			Mono<TaskEntity> savedTask = taskDao.save(task);
			return addTaskToProjectAndSave.apply(savedTask);
		};
		return CompletableFuture.supplyAsync(save);
	}

	@Override
	public Mono<ProjectEntity> addTask(TaskEntity task) {
		Mono<DeveloperEntity> assignedTo = developerService.findOne(task.getAssignedTo().getId());
		Mono<ProjectEntity> belongsTo = findOne(task.getProject().getId());
		assignedTo.subscribe(user -> {
			task.setAssignedTo(user);
		});
		belongsTo.subscribe(project -> {
			task.setProject(project);
		});
		return Mono.fromFuture(saveAndUpdateTask(belongsTo, task));
	}
	
	@Override
	public Mono<ProjectEntity> updateTask(TaskEntity task) {
		Mono<ProjectEntity> belongsTo = findOne(task.getProject().getId());
		Mono<TaskEntity> foundTask = taskDao.findById(task.getId());
		Consumer<TaskEntity> updateWithRestriction = oldTask -> {
			task.setStatus(oldTask.getStatus());
			task.setComments(oldTask.getComments());
			task.setProject(oldTask.getProject());
			task.setAssignedTo(oldTask.getAssignedTo());
			task.setCreatedAt(oldTask.getCreatedAt());
			task.setUpdatedAt(oldTask.getUpdatedAt());
		};
		foundTask.subscribe(updateWithRestriction);
		return Mono.fromFuture(saveAndUpdateTask(belongsTo, task));
	}

	@Override
	public Mono<ProjectEntity> switchProjectStatus(String projectId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Mono<ProjectEntity> commentTask(TaskEntity task) {
		return null;
	}

	@Override
	public Mono<ProjectEntity> requestJoin(String projectId, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ProjectEntity> acceptJoinRequest(String projectId, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ProjectEntity> rejectJoinRequest(String projectId, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
