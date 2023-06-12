package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomTeamRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.TeamNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface TeamCrudRepository extends Neo4jRepository<TeamNode, String>, CustomTeamRepository {

    TeamNode findByCode(String teamCode);

    @Query("MATCH(t:Team{code:$teamcode})-[r]-(p:People) RETURN p.code")
    List<Long> findMembersByTeamCode(String teamcode);

}
