package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customRepository.CustomOpportunityRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface OpportunityCrudRepository extends Neo4jRepository<OpportunityNode, String>, CustomOpportunityRepository {

  OpportunityNode findByCode(String opportunitycode);

  List<OpportunityNode> findByDeletedIsFalse();
}
