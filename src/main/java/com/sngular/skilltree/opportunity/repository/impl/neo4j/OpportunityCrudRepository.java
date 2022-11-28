package com.sngular.skilltree.opportunity.repository.impl.neo4j;

import com.sngular.skilltree.opportunity.model.Opportunity;
import com.sngular.skilltree.opportunity.repository.impl.neo4j.model.OpportunityNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface OpportunityCrudRepository extends Neo4jRepository<OpportunityNode, String> {


  OpportunityNode findByCode(String opportunitycode);
}
