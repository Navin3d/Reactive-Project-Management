package gmc.learning.reactive.management.project.batch;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.models.MailModel;
import gmc.learning.reactive.management.project.services.MailService;

@Service
public class BatchService {

	@Autowired
	private List<BatchRequest> batchRequests;

	@Autowired
	private MailService mailService;

	public void handleFileUploads(BatchRequest batchRequest) {
		batchRequests.add(batchRequest);
		MailModel batchCreationMail = new MailModel(batchRequest.userEmail(), "Files Uploaded Project Manager",
				"If you want to terminate your action please terminate using link http://localhost:3000/batch/terminate/"
						+ batchRequest.id() + " this was uploaded at " + batchRequest.requestedTime()
						+ " with files count " + batchRequest.fileNames().size());
		mailService.sendMail(batchCreationMail);
	}

	public void terminateUserTask(String batchId) {
		BatchRequest terminatingBatch = getBatchRequest(batchId);
		clearBatchRecedues(terminatingBatch);
		
		MailModel batchTerminationMail = new MailModel(terminatingBatch.userEmail(), "Cancelled processing",
				"Cancelled requested received at " + terminatingBatch.requestedTime() + " with file count of "
						+ terminatingBatch.fileNames().size());
		mailService.sendMail(batchTerminationMail);
	}
	
	public void completeUserTask(String batchId) {
		BatchRequest terminatingBatch = getBatchRequest(batchId);
		clearBatchRecedues(terminatingBatch);
		
		MailModel batchTerminationMail = new MailModel(terminatingBatch.userEmail(), "Completed processing",
				" requested at " + terminatingBatch.requestedTime() + " with file count of "
						+ terminatingBatch.fileNames().size());
		mailService.sendMail(batchTerminationMail);
	}
	
	private void clearBatchRecedues(BatchRequest batch) {
		batchRequests.remove(batch);
		deleteFolder(batch.storedLocation());
	}
	
	private BatchRequest getBatchRequest(String batchId) {
		return batchRequests.stream().filter(request -> request.id() == batchId).findFirst()
				.get();
	}

	private void deleteFolder(String folderPathString) {
		Path folderPath = Paths.get(folderPathString);
		try {
			Files.walk(folderPath).sorted(Comparator.reverseOrder()).forEach(path -> {
				try {
					Files.delete(path);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Files.delete(folderPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
