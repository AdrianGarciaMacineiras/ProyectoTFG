package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import org.springframework.data.neo4j.repository.query.Query;

public interface CustomOpportunityRepository {

    @Query("MATCH(n:Opportunity{code:$opportunitycode}) RETURN n")
    OpportunityNode findOpportunity(String opportunitycode);

    @Query("MATCH(n:Opportunity{code:opportunitycode}-[r]-(p:Candidate) DETACH DELETE n,p")
    void detachDelete(String opportunitycode);
}
