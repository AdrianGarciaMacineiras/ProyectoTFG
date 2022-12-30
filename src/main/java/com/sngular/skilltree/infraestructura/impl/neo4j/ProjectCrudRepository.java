package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ProjectCrudRepository extends Neo4jRepository<ProjectNode, Long> {

    ProjectNode findByCode(String projectcode);
}
