package gmc.learning.scala.management.project.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Data;

@Document
public @Data class TaskEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@MongoId
	private String id;
	
	private String tittle;
	
	private String description;
	
	private List<String> comments = new ArrayList<>();
	
	private Boolean status;
	
	private LocalDate deadline;
	
	@DBRef
	private DeveloperEntity assignedTo;
	
	@DBRef
	private ProjectEntity belongsTo;

}
