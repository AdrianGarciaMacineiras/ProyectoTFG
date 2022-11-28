package com.sngular.skilltree.opportunity.repository.neo4j.repository;

import com.sngular.skilltree.opportunity.model.Opportunity;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface OpportunityRepository extends ReactiveNeo4jRepository<Opportunity, String> {


}
