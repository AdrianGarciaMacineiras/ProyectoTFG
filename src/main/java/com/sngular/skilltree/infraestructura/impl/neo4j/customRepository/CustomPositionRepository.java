package com.sngular.skilltree.infraestructura.impl.neo4j.customRepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PositionNode;
import org.springframework.data.neo4j.repository.query.Query;

public interface CustomPositionRepository {

    @Query("MATCH(n:Position{code:$positionCode}) RETURN n")
    PositionNode findPosition(String positionCode);

    @Query("MATCH(n:Position)-[r]-(p:Project{name:$projectcode}) RETURN n")
    PositionNode findPositionByProject(String projectcode);

    @Query("MATCH(p:Position)-[r:CANDIDATE{code:$candidateCode}]-() RETURN p")
    PositionNode findPositionByCandidate(String candidateCode);
}
