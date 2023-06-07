package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomTeamRepository {

    @Query("MATCH(t:Team{code:$teamcode})-[r]-(p:People) RETURN p.code")
    List<Long> findMembersByTeamCode(String teamcode);

}
