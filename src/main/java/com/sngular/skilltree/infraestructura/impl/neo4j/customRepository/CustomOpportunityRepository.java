package com.sngular.skilltree.infraestructura.impl.neo4j.customRepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import org.springframework.data.neo4j.repository.query.Query;

public interface CustomOpportunityRepository {

    @Query("MATCH(n:Opportunity{code:$opportunityCode}) RETURN n")
    OpportunityNode findOpportunity(String opportunityCode);

}
