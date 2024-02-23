package gmc.learning.reactive.management.project.batch;

import org.springframework.batch.item.ItemProcessor;

import gmc.learning.reactive.management.project.entities.TaskEntity;

public class TaskItemProcessor implements ItemProcessor<TaskRecord, TaskEntity> {

	@Override
	public TaskEntity process(TaskRecord item) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
