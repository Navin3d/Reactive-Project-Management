package gmc.learning.reactive.management.project.parser;

import java.io.InputStream;
import java.util.List;

import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;

public interface FileService {
	
	public final String SPLIT_VALUE = "||";

	public List<DeveloperEntity> parseDevelopers(InputStream inputStram) throws Exception ;
	public List<ProjectEntity> parseProjects(InputStream inputStram) throws Exception ;
	public List<TaskEntity> parseTasks(InputStream inputStram) throws Exception ;
	
}
