package gmc.learning.reactive.management.project.services.impl;

import java.util.List;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class GraphQLServiceImpl implements GraphQLService {
	
	@Autowired
	private DeveloperDao developerDao;
	
	@Autowired
	private ProjectService projectDao;
	
	@Autowired
	private TaskDao taskDao;
	
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
			List<TaskEntity> tasks = tuple.getT1();
			List<DeveloperEntity> requests = tuple.getT2();
			List<DeveloperEntity> developer = tuple.getT3();
			
			returnValue.setTasks(tasks);
			returnValue.setDevelopers(developer);
			returnValue.setRequestedDevelopers(requests);
			
			return returnValue;
		});
		
		return Mono.from(returnFlux);
	};

	@Override
	public Flux<ProjectGraphModel> projects(Integer page, Integer size) {
		Flux<ProjectEntity> projects = projectDao.findMany(page, size, true);
		return projects.flatMap(projectToGraph);
	}

	@Override
	public Mono<ProjectGraphModel> project(String id) {
		return projectDao.findOne(id).flatMap(projectToGraph);
	}

	@Override
	public Mono<DeveloperGraphModel> developer(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
