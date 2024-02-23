package gmc.learning.reactive.management.project.batch;

import java.time.LocalDateTime;
import java.util.List;

public record BatchRequest(String id, String userEmail, List<String> fileNames, LocalDateTime requestedTime) {

}
