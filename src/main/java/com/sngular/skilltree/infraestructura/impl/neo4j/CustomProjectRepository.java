package com.sngular.skilltree.infraestructura.impl.neo4j;

import org.springframework.data.neo4j.repository.query.Query;

public interface CustomProjectRepository {

    @Query("MATCH(n:Project{code:$projectCode})-[r]-(p:ProjectRole) DETACH DELETE n,p")
    void detachDelete(Long projectCode);
}
