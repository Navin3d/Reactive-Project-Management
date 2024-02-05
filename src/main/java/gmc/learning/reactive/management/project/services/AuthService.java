package gmc.learning.reactive.management.project.services;

import gmc.learning.reactive.management.project.models.DeveloperModel;

public interface AuthService {
	
	public DeveloperModel registerUser(DeveloperModel developerModel);	
	public DeveloperModel completeProfile(DeveloperModel developerModel);

}
