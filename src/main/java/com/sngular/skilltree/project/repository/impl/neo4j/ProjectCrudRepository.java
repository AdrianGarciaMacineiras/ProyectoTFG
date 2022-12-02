package com.sngular.skilltree.project.repository.impl.neo4j;

import com.sngular.skilltree.project.repository.impl.neo4j.model.ProjectNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProjectCrudRepository extends Neo4jRepository<ProjectNode, String> {

    ProjectNode findByCode(String projectcode);
}
