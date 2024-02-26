package com.tfg.skilltree.infraestructura.impl.neo4j;

import java.util.List;

import com.tfg.skilltree.infraestructura.impl.neo4j.model.PositionNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface PositionCrudRepository extends Neo4jRepository<PositionNode, String> {

  PositionNode getByCode(String positionCode);

  List<PositionNode> findByDeletedIsFalse();

  @Query("MATCH(n:Position{code:$positionCode}) RETURN n")
  PositionNode findPosition(String positionCode);

  List<PositionNode> findByName(String positionName);

  boolean existsByCodeAndDeletedFalse(String positionCode);

}