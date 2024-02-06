
package gmc.learning.reactive.management.project.services.impl;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.dao.DeveloperDao;
import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.models.DeveloperModel;
import gmc.learning.reactive.management.project.services.AuthService;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private DeveloperDao developerDao;

	@Override
	public Mono<DeveloperModel> registerUser(DeveloperModel developerModel) {
		DeveloperEntity newUser = new DeveloperEntity();
		newUser.setName(developerModel.getName());
		newUser.setEmail(developerModel.getEmail());
		newUser.setAuthProvider(developerModel.getAuthProvider());
		if(developerModel.getAuthProvider().equals("Native")) 
			newUser.setPassword(developerModel.getPassword());
		Function<DeveloperEntity, Mono<DeveloperModel>> saveAndConvert = saved -> {
			developerModel.setId(saved.getId());
			return Mono.just(developerModel);
		};
		Mono<DeveloperEntity> savedDeveloper = developerDao.save(newUser);
		Mono<DeveloperModel> returnValue = savedDeveloper.flatMap(saveAndConvert);
		return returnValue;
	}

	@Override
	public Mono<DeveloperModel> completeProfile(DeveloperModel developerModel) {
		DeveloperEntity newUser = new DeveloperEntity();
		newUser.setProfilePicUrl(developerModel.getProfilePicUrl());
		newUser.setUsername(developerModel.getUsername());
		newUser.setGithubProfile(developerModel.getGithubProfile());
		newUser.setLinkedInProfile(developerModel.getLinkedInProfile());
		Function<DeveloperEntity, Mono<DeveloperModel>> saveAndConvert = saved -> {
			developerModel.setId(saved.getId());
			return Mono.just(developerModel);
		};
		Mono<DeveloperEntity> savedDeveloper = developerDao.save(newUser);
		Mono<DeveloperModel> returnValue = savedDeveloper.flatMap(saveAndConvert);
		return returnValue;
	}

}
