package com.sngular.skilltree.infraestructura.impl.neo4j;

import java.util.List;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomTeamRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.TeamNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.projection.TeamProjection;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface TeamCrudRepository extends Neo4jRepository<TeamNode, String>, CustomTeamRepository {

    TeamNode findByCode(String teamCode);

    List<TeamProjection> findByDeletedIsFalse();

}
