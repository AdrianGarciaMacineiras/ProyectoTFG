package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.People;

public interface PeopleUpdater {

    People update(final Integer personcode, final People newPeople);

    People patch(final Integer personcode, final People patchedPeople);
}
