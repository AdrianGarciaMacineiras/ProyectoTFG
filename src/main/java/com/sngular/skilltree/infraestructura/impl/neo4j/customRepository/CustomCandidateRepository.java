package com.sngular.skilltree.infraestructura.impl.neo4j.customRepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomCandidateRepository extends Neo4jRepository<CandidateRelationship, Long> {

    @Query("MATCH (n:Opportunity)-[r:CANDIDATE]-(p) RETURN r.code, r.status, r.introductionDate, r.resolutionDate, p")
    List<CandidateRelationshipProjection> findAllCandidates();

}
