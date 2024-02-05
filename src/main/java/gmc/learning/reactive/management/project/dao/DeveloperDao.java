package gmc.learning.reactive.management.project.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import gmc.learning.reactive.management.project.entities.DeveloperEntity;

public interface DeveloperDao extends ReactiveMongoRepository<DeveloperEntity, String> {

}
