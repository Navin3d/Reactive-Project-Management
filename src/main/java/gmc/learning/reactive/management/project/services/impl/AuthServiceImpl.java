
package gmc.learning.reactive.management.project.services.impl;

import java.util.ArrayList;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return developerDao.findByEmail(username)
			       .map(u -> User
			            .withUsername(username).password(u.getPassword())
			            .authorities(new ArrayList<>())
			            .accountExpired(false)
			            .credentialsExpired(false)
			            .disabled(false)
			            .accountLocked(false)
			            .build()
			        );
	}
	
	@Override
	public Mono<DeveloperModel> registerUser(DeveloperModel developerModel) {
		DeveloperEntity newUser = new DeveloperEntity();
		newUser.setName(developerModel.getName());
		newUser.setEmail(developerModel.getEmail());
		newUser.setAuthProvider(developerModel.getAuthProvider());
		if (developerModel.getAuthProvider().equals("Native"))
			newUser.setPassword(bCryptPasswordEncoder.encode(developerModel.getPassword()));
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
