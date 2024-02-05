package gmc.learning.reactive.management.project.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class DeveloperModel implements Serializable {
	
	private static final long serialVersionUID = -1459377816706640970L;
	
	private String id;
	
	private String profilePicUrl;
	
	private String name;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private String githubProfile;
	
	private String linkedInProfile;
	
	private String authProvider;

}
