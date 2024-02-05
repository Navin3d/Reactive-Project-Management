package gmc.learning.scala.management.project.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Document(collection = "projects")
public @Data class ProjectEntity implements Serializable {

	private static final long serialVersionUID = 6694643477023020830L;
	
	@MongoId
	private String id;
	
	private String tittle;
	
	private String description;
	
	private String icon = "https://avatars.githubusercontent.com/u/71096790?v=4";
	
	@DBRef
	private Set<TaskEntity> tasks = new HashSet<>();
	
	@DBRef
	private Set<DeveloperEntity> requestedDevelopers = new HashSet<>();
	
	@DBRef
	private Set<DeveloperEntity> developers = new HashSet<>();
	
}
