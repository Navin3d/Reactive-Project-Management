package gmc.learning.reactive.management.project.parser;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import gmc.learning.reactive.management.project.entities.DeveloperEntity;
import gmc.learning.reactive.management.project.entities.ProjectEntity;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExcelService implements FileService {

	@Override
	public List<DeveloperEntity> parseDevelopers(InputStream inputStram) throws IOException {
		List<DeveloperEntity> developerEntities = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(inputStram);
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;
			developerEntities.add(developerFromRow(row));
		}
		workbook.close();
		inputStram.close();
		return developerEntities;
	}

	@Override
	public List<ProjectEntity> parseProjects(InputStream inputStram) throws IOException {
		List<ProjectEntity> projects = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(inputStram);
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;
			projects.add(projectFromRow(row));
		}
		workbook.close();
		inputStram.close();
		return projects;
	}

	@Override
	public List<TaskEntity> parseTasks(InputStream inputStram) throws IOException {
		List<TaskEntity> tasks = new ArrayList<>();
		Workbook workbook = WorkbookFactory.create(inputStram);
		Sheet sheet = workbook.getSheetAt(0);
		for (Row row : sheet) {
			if (row.getRowNum() == 0)
				continue;
			tasks.add(taskFromRow(row));
		}
		workbook.close();
		inputStram.close();
		return tasks;
	}

	private DeveloperEntity developerFromRow(Row row) {
		DeveloperEntity developer = new DeveloperEntity();

		developer.setId(getStringValue(row.getCell(0)));
		developer.setName(getStringValue(row.getCell(1)));
		developer.setUsername(getStringValue(row.getCell(2)));
		developer.setEmail(getStringValue(row.getCell(3)));
		developer.setProfilePicUrl(getStringValue(row.getCell(4)));

		developer.setGithubProfile(getStringValue(row.getCell(5)));
		developer.setLinkedInProfile(getStringValue(row.getCell(6)));
		developer.setAuthProvider("Google");

		log.info("Parsed Developer: {}", developer.toString());

		return developer;
	}

	private ProjectEntity projectFromRow(Row row) {
		ProjectEntity project = new ProjectEntity();

		project.setId(getStringValue(row.getCell(0)));
		project.setTittle(getStringValue(row.getCell(1)));
		project.setDescription(getStringValue(row.getCell(2)));
		project.setCreatedBy(getStringValue(row.getCell(3)));
		project.setIcon(getStringValue(row.getCell(4)));

		String developers = getStringValue(row.getCell(5));

		project.getDevelopers().addAll(List.of(developers.split(FileService.SPLIT_VALUE)));

		log.info("Parsed Project: {}", project.toString());

		return project;
	}

	private TaskEntity taskFromRow(Row row) {
		TaskEntity task = new TaskEntity();

		task.setId(getStringValue(row.getCell(0)));
		task.setTittle(getStringValue(row.getCell(1)));
		task.setDescription(getStringValue(row.getCell(2)));

		String comments = getStringValue(row.getCell(3));

		task.getComments().addAll(List.of(comments.split(FileService.SPLIT_VALUE)));

		task.setAssignedTo(getStringValue(row.getCell(4)));
		task.setProjectId(getStringValue(row.getCell(5)));

		String dateString = getStringValue(row.getCell(6));
		task.setDeadline(LocalDate.parse(dateString));

		log.info("Parsed task: {}", task.toString());

		return task;
	}

	private String getStringValue(Cell cell) {
		if (cell == null) {
			return null;
		}
//        cell.setCellType(CellType.STRING);
		return cell.getStringCellValue();
	}

}
