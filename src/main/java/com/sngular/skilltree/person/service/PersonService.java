package com.sngular.skilltree.person.service;

import com.sngular.skilltree.person.model.Person;
import java.util.List;

public interface PersonService {

    List<Person> getALl();

    Person create (final Person toPerson);

    Person findByCode(final String personcode);

    boolean deleteByCode(final String personcode);

    Person update(final String personcode, final Person toPerson);

    Person patch(final String personcode, final Person toPerson);
}
