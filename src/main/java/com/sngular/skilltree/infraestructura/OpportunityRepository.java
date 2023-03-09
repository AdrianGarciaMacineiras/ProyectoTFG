package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Opportunity;
import java.util.List;

public interface OpportunityRepository {

  List<Opportunity> findAll();

  Opportunity save(Opportunity opportunity);

  Opportunity findByCode(String opportunitycode);

  boolean deleteByCode(String opportunitycode);

  List<Opportunity> findByDeletedIsFalse();
}
