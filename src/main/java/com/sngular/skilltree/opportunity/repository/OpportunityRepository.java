package com.sngular.skilltree.opportunity.repository;

import com.sngular.skilltree.opportunity.model.Opportunity;
import java.util.List;

public interface OpportunityRepository {

  List<Opportunity> findAll();

  Opportunity save(Opportunity opportunity);

  Opportunity findByCode(String opportunitycode);

  boolean deleteByCode(String opportunitycode);

}
