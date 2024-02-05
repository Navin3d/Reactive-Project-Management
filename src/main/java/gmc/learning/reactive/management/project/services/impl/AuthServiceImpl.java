
package gmc.learning.reactive.management.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.dao.DeveloperDao;
import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.models.DeveloperModel;
import gmc.learning.reactive.management.project.services.AuthService;
import gmc.learning.reactive.management.project.utils.ReactiveUtils;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private DeveloperDao developerDao;

	@Override
	public DeveloperModel registerUser(DeveloperModel developerModel) {
		DeveloperEntity newUser = new DeveloperEntity();
		newUser.setName(developerModel.getName());
		newUser.setEmail(developerModel.getEmail());
		newUser.setAuthProvider(developerModel.getAuthProvider());
		if(developerModel.getAuthProvider().equals("Native")) 
			newUser.setPassword(developerModel.getPassword());
		Mono<DeveloperEntity> savedUser = developerDao.save(newUser);
		developerModel.setId(ReactiveUtils.<DeveloperEntity>monoTo(savedUser).getId());
		return developerModel;
	}

	@Override
	public DeveloperModel completeProfile(DeveloperModel developerModel) {
		DeveloperEntity newUser = new DeveloperEntity();
		newUser.setProfilePicUrl(developerModel.getProfilePicUrl());
		newUser.setUsername(developerModel.getUsername());
		newUser.setGithubProfile(developerModel.getGithubProfile());
		newUser.setLinkedInProfile(developerModel.getLinkedInProfile());
		Mono<DeveloperEntity> savedUser = developerDao.save(newUser);
		developerModel.setId(ReactiveUtils.<DeveloperEntity>monoTo(savedUser).getId());
		return developerModel;
	}

}
