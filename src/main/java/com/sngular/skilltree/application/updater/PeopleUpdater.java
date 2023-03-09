package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.People;

public interface PeopleUpdater {

    People update(final Long personcode, final People newPeople);

    People patch(final Long personcode, final People patchedPeople);
}
