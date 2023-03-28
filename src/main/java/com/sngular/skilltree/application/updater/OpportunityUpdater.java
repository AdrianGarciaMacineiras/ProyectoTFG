package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Puesto;

public interface OpportunityUpdater {

    Puesto update(final String opportunitycode, final Puesto newPuesto);

    Puesto patch(final String opportunitycode, final Puesto patchedPuesto);
}
