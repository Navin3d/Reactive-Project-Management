package gmc.learning.reactive.management.project.batch;

import java.time.LocalDate;
import java.util.List;

import org.springframework.batch.item.ItemProcessor;

import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import gmc.learning.reactive.management.project.parser.FileService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BatchProcessors {
	
	public static ItemProcessor<ProjectRecord, ProjectEntity> project = item -> {
        ProjectEntity project = new ProjectEntity();

        project.setId(item.id());
        project.setTittle(item.tittle());
        project.setDescription(item.description());
        project.setCreatedBy(item.createdBy());
        
        project.getDevelopers().addAll(List.of(item.developers().split(FileService.SPLIT_VALUE)));

        log.info("Parsed Project: {}", project.toString()); // Assuming log is defined somewhere

        return project;
    };
	
	public static ItemProcessor<DeveloperRecord, DeveloperEntity> developer = item -> {
        DeveloperEntity developer = new DeveloperEntity();

        developer.setId(item.id());
        developer.setName(item.name());
        developer.setUsername(item.username());
        developer.setEmail(item.email());
        developer.setAuthProvider("Google");
        log.info("Parsed Developer: {}", developer.toString()); // Assuming log is defined somewhere
        
        return developer;
    };
	
	public static ItemProcessor<TaskRecord, TaskEntity> task = item -> {
        TaskEntity task = new TaskEntity();

        task.setTittle(item.tittle());
        task.setDescription(item.description());

        task.getComments().addAll(List.of(item.comments().split(FileService.SPLIT_VALUE)));

        task.setAssignedTo(item.assignedTo());
        task.setProjectId(item.projectId());
        task.setDeadline(LocalDate.parse(item.deadline()));

        return task;
    };

}
