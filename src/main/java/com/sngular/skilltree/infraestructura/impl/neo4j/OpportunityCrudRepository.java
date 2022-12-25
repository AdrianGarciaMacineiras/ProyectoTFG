package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface OpportunityCrudRepository extends Neo4jRepository<OpportunityNode, String> {


  OpportunityNode findByCode(String opportunitycode);
}
