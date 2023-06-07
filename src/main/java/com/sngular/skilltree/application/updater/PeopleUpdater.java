package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.People;

public interface PeopleUpdater {

    People update(final String personCode, final People newPeople);

    People patch(final String personCode, final People patchedPeople);
}
