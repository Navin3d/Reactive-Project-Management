package gmc.learning.reactive.management.project.batch;

import org.springframework.batch.item.ItemProcessor;

import gmc.learning.reactive.management.project.entities.ProjectEntity;

public class ProjectItemProcessor implements ItemProcessor<ProjectRecord, ProjectEntity> {

	@Override
	public ProjectEntity process(ProjectRecord item) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
