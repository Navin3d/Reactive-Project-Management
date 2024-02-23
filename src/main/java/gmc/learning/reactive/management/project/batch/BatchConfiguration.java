package gmc.learning.reactive.management.project.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class BatchConfiguration {
	
	@Bean
	List<BatchRequest> batchRequests() {
		return new ArrayList<>();
	}

}
