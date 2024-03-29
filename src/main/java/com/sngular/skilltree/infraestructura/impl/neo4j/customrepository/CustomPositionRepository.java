package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PositionNode;
import org.springframework.data.neo4j.repository.query.Query;

public interface CustomPositionRepository {

    @Query("MATCH(n:Position{code:$positionCode}) RETURN n")
    PositionNode findPosition(String positionCode);

    @Query("MATCH(n:Position)-[r]-(p:Project{name:$projectCode}) RETURN n")
    PositionNode findPositionByProject(String projectCode);
}
