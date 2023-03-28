package com.sngular.skilltree.infraestructura.impl.neo4j.customRepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PuestoNode;
import org.springframework.data.neo4j.repository.query.Query;

public interface CustomPuestoRepository {

    @Query("MATCH(n:Opportunity{code:$opportunityCode}) RETURN n")
    PuestoNode findOpportunity(String opportunityCode);

}
