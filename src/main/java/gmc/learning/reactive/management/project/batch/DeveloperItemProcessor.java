package gmc.learning.reactive.management.project.batch;

import org.springframework.batch.item.ItemProcessor;

import gmc.learning.reactive.management.project.entities.DeveloperEntity;

public class DeveloperItemProcessor implements ItemProcessor<DeveloperRecord, DeveloperEntity> {

	@Override
	public DeveloperEntity process(DeveloperRecord item) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
