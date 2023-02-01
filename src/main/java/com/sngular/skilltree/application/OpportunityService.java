package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Opportunity;
import java.util.List;

public interface OpportunityService {

  List<Opportunity> getAll();

  Opportunity create(final Opportunity toOpportunity);

  Opportunity findByCode(final String opportunitycode);

  boolean deleteByCode(final String opportunitycode);

  Opportunity update(final String opportunitycode, final Opportunity newOpportunity);

  Opportunity patch(final String opportunitycode, final Opportunity patchedOpportunity);
}
