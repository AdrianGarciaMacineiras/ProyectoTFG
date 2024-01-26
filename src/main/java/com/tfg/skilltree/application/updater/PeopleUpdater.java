package com.tfg.skilltree.application.updater;

import com.tfg.skilltree.model.People;

public interface PeopleUpdater {

    People update(final String personCode, final People newPeople);

    People patch(final String personCode, final People patchedPeople);
}
