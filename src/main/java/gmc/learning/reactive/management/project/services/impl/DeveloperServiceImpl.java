package gmc.learning.reactive.management.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.dao.DeveloperDao;
import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.services.DeveloperService;
import reactor.core.publisher.Mono;

@Service
public class DeveloperServiceImpl implements DeveloperService {
	
	@Autowired
	private DeveloperDao developerDao;

	@Override
	public Mono<DeveloperEntity> findOne(String uniqueId) {
		return developerDao.findById(uniqueId);
	}

}
