package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Opportunity;

public interface OpportunityUpdater {

    Opportunity update(final String opportunitycode, final Opportunity newOpportunity);

    Opportunity patch(final String opportunitycode, final Opportunity patchedOpportunity);
}
