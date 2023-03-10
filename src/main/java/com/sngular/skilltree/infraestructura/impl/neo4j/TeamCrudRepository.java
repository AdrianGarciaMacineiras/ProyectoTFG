package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.TeamNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface TeamCrudRepository extends Neo4jRepository<TeamNode, String>, CustomTeamRepository{

    TeamNode findByCode(String teamcode);

    List<TeamNode> findByDeletedIsFalse();
}
