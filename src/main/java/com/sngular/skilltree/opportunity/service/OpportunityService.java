package com.sngular.skilltree.opportunity.service;

import com.sngular.skilltree.opportunity.model.Opportunity;
import java.util.List;

public interface OpportunityService {

  List<Opportunity> getAll();

  Opportunity create(final Opportunity toOpportunity);

  Opportunity findByCode(final String opportunitycode);

  boolean deleteBeCode(final String opportunitycode);

  Opportunity update(final String opportunitycode, final Opportunity toOpportunity);

  Opportunity patch(final String opportunitycode, final Opportunity toOpportunity);
}
