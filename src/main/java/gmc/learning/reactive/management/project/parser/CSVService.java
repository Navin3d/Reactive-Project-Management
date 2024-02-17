package gmc.learning.reactive.management.project.parser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CSVService implements FileService {

	@Override
	public List<DeveloperEntity> parseDevelopers(InputStream inputStram) throws Exception {
		List<DeveloperEntity> returnValue = new ArrayList<>();
		Reader reader = new InputStreamReader(inputStram);
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .build();

        List<String[]> rows = csvReader.readAll();

        for (String[] row : rows)
            returnValue.add(developerFromRow(row));
		return returnValue;
	}

	@Override
	public List<ProjectEntity> parseProjects(InputStream inputStram) throws Exception {
		List<ProjectEntity> returnValue = new ArrayList<>();
		Reader reader = new InputStreamReader(inputStram);
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .build();

        List<String[]> rows = csvReader.readAll();

        for (String[] row : rows)
            returnValue.add(projectFromRow(row));
		return returnValue;
	}

	@Override
	public List<TaskEntity> parseTasks(InputStream inputStram) throws Exception {
		List<TaskEntity> returnValue = new ArrayList<>();
		Reader reader = new InputStreamReader(inputStram);
        CSVReader csvReader = new CSVReaderBuilder(reader)
                .withSkipLines(1)
                .build();

        List<String[]> rows = csvReader.readAll();

        for (String[] row : rows)
            returnValue.add(taskFromRow(row));
		return returnValue;
	}
	
	private DeveloperEntity developerFromRow(String[] row) {
		DeveloperEntity developer = new DeveloperEntity();

		developer.setId(row[0]);
		developer.setName(row[1]);
		developer.setUsername(row[2]);
		developer.setEmail(row[3]);
		developer.setProfilePicUrl(row[4]);

		developer.setGithubProfile(row[5]);
		developer.setLinkedInProfile(row[6]);
		developer.setAuthProvider("Google");

		log.info("Parsed Developer: {}", developer.toString());

		return developer;
	}

	private ProjectEntity projectFromRow(String[] row) {
		ProjectEntity project = new ProjectEntity();

		project.setId(row[0]);
		project.setTittle(row[1]);
		project.setDescription(row[2]);
		project.setCreatedBy(row[3]);
		project.setIcon(row[4]);

		project.getDevelopers().addAll(List.of(row[5].split(FileService.SPLIT_VALUE)));

		log.info("Parsed Project: {}", project.toString());

		return project;
	}

	private TaskEntity taskFromRow(String[] row) {
		TaskEntity task = new TaskEntity();

		task.setId(row[0]);
		task.setTittle(row[1]);
		task.setDescription(row[2]);

		task.getComments().addAll(List.of(row[3].split(FileService.SPLIT_VALUE)));

		task.setAssignedTo(row[4]);
		task.setProjectId(row[5]);
		task.setDeadline(LocalDate.parse(row[6]));

		log.info("Parsed task: {}", task.toString());

		return task;
	}

}
