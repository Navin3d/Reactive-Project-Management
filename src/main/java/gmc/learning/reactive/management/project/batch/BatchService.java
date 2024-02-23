package gmc.learning.reactive.management.project.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BatchService {

	@Autowired
	private List<BatchRequest> batchRequests;

	public void terminateUserTask(String batchId) {
		BatchRequest terminatingBatch = batchRequests.stream().filter(request -> request.id() == batchId).findFirst().get();
		deleteFiles(terminatingBatch.fileNames());
		batchRequests.remove(terminatingBatch);
	}
	
	private void deleteFiles(List<String> fileName) {
		
	}

}
