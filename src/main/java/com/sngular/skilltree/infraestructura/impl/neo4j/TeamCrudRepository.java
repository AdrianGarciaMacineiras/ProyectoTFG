package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.TeamNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TeamCrudRepository extends Neo4jRepository<TeamNode, String> {

    TeamNode findByCode(String teamcode);
}
