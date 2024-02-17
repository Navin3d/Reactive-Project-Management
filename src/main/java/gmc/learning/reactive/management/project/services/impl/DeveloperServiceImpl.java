package gmc.learning.reactive.management.project.services.impl;

import java.util.List;
import java.util.Optional;

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
		return developerDao.findById(uniqueId).map(Optional::of)
				.flatMap(user -> {
						if(user.isPresent()) 
							return Mono.just(user.get()); 
						else 
							return developerDao.findByEmail(uniqueId);
					});
	}

	@Override
	public Mono<DeveloperEntity> save(DeveloperEntity developerEntity) {
		return developerDao.save(developerEntity);
	}

	@Override
	public void saveAll(List<DeveloperEntity> developerEntities) {
		developerDao.saveAll(developerEntities);
	}

}
