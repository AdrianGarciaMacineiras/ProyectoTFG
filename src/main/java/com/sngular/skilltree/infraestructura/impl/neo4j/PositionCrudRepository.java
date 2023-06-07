package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomPositionRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PositionNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface PositionCrudRepository extends Neo4jRepository<PositionNode, String>, CustomPositionRepository {

    PositionNode findByCode(String positionCode);

  List<PositionNode> findByDeletedIsFalse();
}
