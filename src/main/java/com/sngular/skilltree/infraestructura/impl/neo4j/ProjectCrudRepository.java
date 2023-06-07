package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomProjectRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface ProjectCrudRepository extends Neo4jRepository<ProjectNode, Long>, CustomProjectRepository {

    ProjectNode findByCode(String projectCode);

    List<ProjectNode> findByDeletedIsFalse();

    ProjectNode findByName(String name);
}
