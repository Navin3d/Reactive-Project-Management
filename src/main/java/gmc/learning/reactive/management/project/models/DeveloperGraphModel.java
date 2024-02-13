package gmc.learning.reactive.management.project.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import lombok.Data;

@Data
public class DeveloperGraphModel implements Serializable {
	
	private static final long serialVersionUID = 8554517519694149552L;
	
	private String id;
	
	private String profilePicUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRDtd0soCSRdpo8Y5klekJdABh4emG2P29jwg&usqp=CAU";
	
	private String name;
	
	private String username;
	
	private String email;
	
	private String githubProfile;
	
	private String linkedInProfile;
	
	private Set<TaskEntity> tasks = new HashSet<>();
	
	private Set<ProjectEntity> projects = new HashSet<>();
	
	private Set<ProjectEntity> requestedProjects = new HashSet<>();
	
}
