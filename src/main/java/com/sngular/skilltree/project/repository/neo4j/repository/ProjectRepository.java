package com.sngular.skilltree.project.repository.neo4j.repository;

import com.sngular.skilltree.project.model.Project;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface ProjectRepository extends ReactiveNeo4jRepository<Project, String> {

}
