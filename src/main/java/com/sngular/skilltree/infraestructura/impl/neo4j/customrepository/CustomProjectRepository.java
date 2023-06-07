package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import org.springframework.data.neo4j.repository.query.Query;

public interface CustomProjectRepository {

    @Query("MATCH(n:Project{code:$projectCode}) RETURN n")
    ProjectNode findProject(String projectCode);
}
