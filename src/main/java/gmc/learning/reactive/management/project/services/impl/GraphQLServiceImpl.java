package gmc.learning.reactive.management.project.services.impl;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.dao.DeveloperDao;
import gmc.learning.reactive.management.project.dao.TaskDao;
import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import gmc.learning.reactive.management.project.models.DeveloperGraphModel;
import gmc.learning.reactive.management.project.models.ProjectGraphModel;
import gmc.learning.reactive.management.project.services.GraphQLService;
import gmc.learning.reactive.management.project.services.ProjectService;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class GraphQLServiceImpl implements GraphQLService {
	
	@Autowired
	private DeveloperDao developerDao;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TaskDao taskDao;

	@Override
	public Flux<ProjectGraphModel> projects() {
		Flux<ProjectEntity> projects = projectService.findManyByStatus(true);
		projects.count().subscribe(count -> {
			log.error("count: {}", count);
		});
		return projects.flatMap(projectToGraph);
	}

	@Override
	public Mono<ProjectGraphModel> project(String id) {
		return projectService.findOne(id).flatMap(projectToGraph);
	}

	@Override
	public Mono<DeveloperGraphModel> developer(String id) {
		Mono<DeveloperEntity> developerEntity = developerDao.findById(id);
		Flux<TaskEntity> userTasks = taskDao.findByAssignedTo(id);
		Flux<ProjectEntity> requested = projectService.findManyByDevelopersRequested(id);
		Flux<ProjectEntity> projects = projectService.findManyByDeveloper(id);
		Flux<ProjectEntity> adminOfProjects = projectService.findManyByAdminId(id);
		return developerEntity.flatMap(developer -> {
			DeveloperGraphModel developerGraphModel = new DeveloperGraphModel(developer);		
			Flux<DeveloperGraphModel> returnValue = Flux.zip(userTasks.collectList(), requested.collectList(), projects.collectList(), adminOfProjects.collectList())
					.map(tuple -> {
						developerGraphModel.setTasks(tuple.getT1());
						developerGraphModel.setRequestedProjects(tuple.getT2());
						developerGraphModel.setProjects(tuple.getT3());
						developerGraphModel.setCreatedProjects(tuple.getT4());
						return developerGraphModel;
					});
			return returnValue.next();
		});
	}
	
	private Function<ProjectEntity, Mono<ProjectGraphModel>> projectToGraph = (project) -> {
		Mono<DeveloperEntity> projectOwner = developerDao.findById(project.getCreatedBy());
		ProjectGraphModel returnValue = new ProjectGraphModel(project);
		projectOwner.subscribe(owner -> {	
			returnValue.setCreatedBy(owner);
		});
		Flux<DeveloperEntity> requestedUsers = developerDao.findAllById(project.getRequestedDevelopers());
		Flux<DeveloperEntity> developers = developerDao.findAllById(project.getRequestedDevelopers());
		Flux<TaskEntity> projectTasks = taskDao.findByProjectId(project.getId());		
		Flux<ProjectGraphModel> returnFlux = Flux.zip(projectTasks.collectList(), developers.collectList(), requestedUsers.collectList()).map(tuple -> {		
			returnValue.setTasks(tuple.getT1());
			returnValue.setDevelopers(tuple.getT2());
			returnValue.setRequestedDevelopers(tuple.getT3());			
			return returnValue;
		});
		return returnFlux.next();
	};

}
