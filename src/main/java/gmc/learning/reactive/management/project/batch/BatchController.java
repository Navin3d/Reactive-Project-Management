package gmc.learning.reactive.management.project.batch;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gmc.learning.reactive.management.project.config.AppSettings;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequestMapping(path = "/batch")
@RestController
public class BatchController {

	@Autowired
	private AppSettings settings;

	@Autowired
	private BatchService batchService;

	@PostMapping(path = "/upload/{uploaderMail}")
	public Mono<String> process(@PathVariable String uploaderMail, @RequestBody Flux<FilePart> files) {
		String requestId = UUID.randomUUID().toString();
		String storedPath = settings.getBatchFilesPath() + "/" + uploaderMail + "/" + requestId + "/";
		Path uploadPath = Paths.get(storedPath);
		List<String> fileNames = new ArrayList<>();
		return files.flatMap(part -> {
			fileNames.add(part.filename());
			return part.transferTo(uploadPath.resolve(part.filename()));
		}).flatMap(data -> {
			BatchRequest batchRequest = new BatchRequest(requestId, uploaderMail, fileNames, storedPath, LocalDateTime.now());
			batchService.handleFileUploads(batchRequest);
			return Mono.just("Files uploaded successfully " + batchRequest.id());
		}).next().onErrorResume(error -> Mono.just("Error occurred during file upload: " + error.getMessage()));
	}

	@GetMapping(path = "/{batchId}")
	public void cancelUserProcess(@PathVariable String batchId) {
		batchService.terminateUserTask(batchId);
	}

}
