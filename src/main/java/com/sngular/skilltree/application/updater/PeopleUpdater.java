package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.People;

public interface PeopleUpdater {

    People update(final String personcode, final People newPeople);

    People patch(final String personcode, final People patchedPeople);
}
