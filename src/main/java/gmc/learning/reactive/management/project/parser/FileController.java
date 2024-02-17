package gmc.learning.reactive.management.project.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import gmc.learning.reactive.management.project.services.DeveloperService;
import gmc.learning.reactive.management.project.services.ProjectService;
import gmc.learning.reactive.management.project.services.TaskService;

@RestController
@RequestMapping("/upload")
public class FileController {

	@Autowired
	private List<FileService> fileParsingServices;
	@Autowired
	private DeveloperService developerService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private TaskService taskService;

	@PostMapping
	@ResponseBody
	public ResponseEntity<List<Object>> handleFileUpload(@RequestParam("files") MultipartFile[] files) {
		List<Object> returValue = new ArrayList<>();
		try {
			for (MultipartFile file : Arrays.asList(files)) {
				if (file.isEmpty())
					return ResponseEntity.badRequest().body(returValue);

				String fileName = file.getOriginalFilename();
				if (!(fileName.contains(".csv") || fileName.contains(".xlsx")))
					return ResponseEntity.badRequest().body(returValue);

				for (FileService service : fileParsingServices) {
					InputStream stream = file.getInputStream();
					try {
						if (fileName.contains("Developers"))
							returValue.addAll(handleDeveloperFile(stream, service));
						if (fileName.contains("Projects"))
							returValue.addAll(handleProjectFile(stream, service));
						if (fileName.contains("Tasks"))
							returValue.addAll(handleTaskFile(stream, service));
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

			return ResponseEntity.ok(returValue);
		} catch (IOException e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(returValue);
		}
	}

	private List<DeveloperEntity> handleDeveloperFile(InputStream inputStream, FileService service) throws Exception  {
		List<DeveloperEntity> dev = service.parseDevelopers(inputStream);
		developerService.saveAll(dev);
		return dev;
	}

	private List<ProjectEntity> handleProjectFile(InputStream inputStream, FileService service) throws Exception  {
		List<ProjectEntity> prj = service.parseProjects(inputStream);
		projectService.saveAll(prj);
		return prj;
	}

	private List<TaskEntity> handleTaskFile(InputStream inputStream, FileService service) throws Exception  {
		List<TaskEntity> tsk = service.parseTasks(inputStream);
		taskService.saveAll(tsk);
		return tsk;
	}

}
