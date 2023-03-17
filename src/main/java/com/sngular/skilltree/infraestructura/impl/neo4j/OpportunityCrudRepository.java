package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customRepository.CandidateRelationshipProjection;
import com.sngular.skilltree.infraestructura.impl.neo4j.customRepository.CustomOpportunityRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface OpportunityCrudRepository extends Neo4jRepository<OpportunityNode, String>, CustomOpportunityRepository {

  OpportunityNode findByCode(String opportunitycode);

  List<OpportunityNode> findByDeletedIsFalse();

  @Query("MATCH (n:Opportunity)-[r:CANDIDATE]-(p) RETURN r.code, r.status, r.introductionDate, r.resolutionDate, p")
  List<CandidateRelationshipProjection> findAllCandidates();

}
