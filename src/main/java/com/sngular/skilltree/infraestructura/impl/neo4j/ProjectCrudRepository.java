package com.sngular.skilltree.infraestructura.impl.neo4j;

import java.util.List;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface ProjectCrudRepository extends Neo4jRepository<ProjectNode, String>{

    ProjectNode findByCode(String projectCode);

    List<ProjectNode> findByDeletedIsFalse();

    ProjectNode findByName(String name);

    @Query("MATCH(n:Project{code:$projectCode}) RETURN n")
    ProjectNode findProject(String projectCode);
}
