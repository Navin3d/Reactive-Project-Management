package gmc.learning.reactive.management.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.dao.ProjectDao;
import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import gmc.learning.reactive.management.project.services.ProjectService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProjectServiceImpl implements ProjectService {
	
	@Autowired
	private ProjectDao projectDao;

	@Override
	public Mono<ProjectEntity> findOne(String id) {
		return projectDao.findById(id);
	}

	@Override
	public Flux<ProjectEntity> findMany(Integer page, Integer size, Boolean status) {
		Pageable pageConfig = PageRequest.of(page, size, Sort.by("tittle"));
		return projectDao.findByStatus(status, pageConfig);
	}

	@Override
	public Mono<ProjectEntity> save(ProjectEntity newProject) {
		return projectDao.save(newProject);
	}

	@Override
	public Mono<ProjectEntity> saveTask(String projectId, TaskEntity task) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ProjectEntity> reOpenProject(String projectId, Boolean status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ProjectEntity> closeProject(String projectId, Boolean status) {
		// TODO Auto-generated method stub
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
