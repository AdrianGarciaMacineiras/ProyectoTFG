package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import org.springframework.data.neo4j.repository.query.Query;

public interface CustomProjectRepository {

    @Query("MATCH(n:Project{code:$projectCode})-[r]-(p:ProjectRole) DETACH DELETE n,p")
    void detachDelete(Long projectCode);

    @Query("MATCH(n:Project{code:$projectCode}) RETURN n")
    ProjectNode findProject(Long projectCode);
}
