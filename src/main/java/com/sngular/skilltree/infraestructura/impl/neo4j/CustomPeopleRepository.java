package com.sngular.skilltree.infraestructura.impl.neo4j;

import org.springframework.data.neo4j.repository.query.Query;

public interface CustomPeopleRepository {

    @Query("MATCH(n:People{code: $peopleCode}) DETACH DELETE n")
    void detachDelete(Long peopleCode);
}
