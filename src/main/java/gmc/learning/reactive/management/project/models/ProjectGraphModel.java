package gmc.learning.reactive.management.project.models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import lombok.Data;

@Data
public class ProjectGraphModel implements Serializable {
	
	private static final long serialVersionUID = -1318727392845400532L;
	
	private String id;
	
	private String tittle;
	
	private String description;
	
	private String icon;
	
	private Boolean status;
	
	private Set<TaskEntity> tasks = new HashSet<>();
	
	private Set<DeveloperEntity> requestedDevelopers = new HashSet<>();
	
	private Set<DeveloperEntity> developers = new HashSet<>();
	
    private DeveloperGraphModel createdBy;

}
