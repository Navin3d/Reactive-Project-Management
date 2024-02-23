package gmc.learning.reactive.management.project.batch;

import java.io.Serializable;

public record TaskRecord(String tittle, String description, String assignedTo, String projectId, String deadline) implements Serializable {

}
