package gmc.learning.reactive.management.project.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import gmc.learning.reactive.management.project.dao.TaskDao;
import gmc.learning.reactive.management.project.entities.TaskEntity;
import reactor.core.publisher.Flux;

@SpringBootTest
public class TaskDaoTest {
	
	@Autowired
	private TaskDao taskDao;

	@Test
	public void itShouldConnectDB() {
		Flux<TaskEntity> tasks = taskDao.findByUser("string");
		assertEquals(0, tasks.collectList().block().size());
	}
	
}
