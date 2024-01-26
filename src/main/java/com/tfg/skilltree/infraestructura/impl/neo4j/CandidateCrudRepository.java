package com.tfg.skilltree.infraestructura.impl.neo4j;

import com.tfg.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface CandidateCrudRepository extends Neo4jRepository<CandidateRelationship, String> {

    CandidateRelationship findByCode(String candidateCode);

    @Query("MATCH(p:Position{code:$positionCode})-[r:COVER{dedication:100}]-(s) RETURN r")
    CandidateRelationship findAssignedByPosition(String positionCode);

}
