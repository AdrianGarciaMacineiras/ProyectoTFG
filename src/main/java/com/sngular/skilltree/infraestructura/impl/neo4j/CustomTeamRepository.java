package com.sngular.skilltree.infraestructura.impl.neo4j;

import org.springframework.data.neo4j.repository.query.Query;

public interface CustomTeamRepository {

    @Query("MATCH(n:Team{code: $teamCode}) DETACH DELETE n")
    void detachDelete(String teamCode);
}
