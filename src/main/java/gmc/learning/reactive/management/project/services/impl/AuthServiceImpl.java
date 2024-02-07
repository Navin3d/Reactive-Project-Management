
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

	@Autowired(required = true)
	private DeveloperDao developerDao;

	@Override
	public Mono<DeveloperModel> registerUser(DeveloperModel developerModel) {
		DeveloperEntity newUser = new DeveloperEntity();
		newUser.setName(developerModel.getName());
		newUser.setEmail(developerModel.getEmail());
		newUser.setAuthProvider(developerModel.getAuthProvider());
		if (developerModel.getAuthProvider().equals("Native"))
			newUser.setPassword(developerModel.getPassword());
		Function<DeveloperEntity, Mono<DeveloperModel>> saveAndConvert = saved -> {
			developerModel.setId(saved.getId());
			return Mono.just(developerModel);
		};
		return developerDao.save(newUser).flatMap(saveAndConvert);
	}

	@Override
	public Mono<DeveloperModel> completeProfile(DeveloperModel developerModel) {
		Mono<DeveloperEntity> requestingDeveloper = developerDao.findById(developerModel.getId());
		Mono<DeveloperEntity> updatedDeveloper = requestingDeveloper.mapNotNull(developer -> {
			developer.setProfilePicUrl(developerModel.getProfilePicUrl());
			developer.setUsername(developerModel.getUsername());
			developer.setGithubProfile(developerModel.getGithubProfile());
			developer.setLinkedInProfile(developerModel.getLinkedInProfile());
			return developer;
		});
		Mono<DeveloperEntity> savedDeveloper = updatedDeveloper.flatMap(developer -> developerDao.save(developer));
		Mono<DeveloperModel> returnValue = savedDeveloper.map(saved -> {
			developerModel.setEmail(saved.getEmail());
			developerModel.setName(saved.getName());
			developerModel.setAuthProvider(saved.getAuthProvider());
			return developerModel;
		});
		return returnValue;
	}

}
