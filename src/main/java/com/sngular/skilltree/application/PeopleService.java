package com.sngular.skilltree.application;

import com.sngular.skilltree.model.People;

import java.util.List;

public interface PeopleService {

    List<People> getAll();

    People create (final People toPeople);

    People findByCode(final String personcode);

    boolean deleteByCode(final String personcode);

    People update(final String personcode, final People newPeople);

    People patch(final String personcode, final People patchedPeople);
}
