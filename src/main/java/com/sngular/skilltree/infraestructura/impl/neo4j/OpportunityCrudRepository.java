package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.OpportunityNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.projections.OpportunityProjection;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.Collection;
import java.util.List;

public interface OpportunityCrudRepository extends Neo4jRepository<OpportunityNode, String>, CustomOpportunityRepository {

  OpportunityNode findByCode(String opportunitycode);

  List<OpportunityNode> findByDeletedIsFalse();
}
