package com.sngular.skilltree.person.repository;

import com.sngular.skilltree.person.model.Person;
import java.util.List;

public interface PersonRepository {

    List<Person> findAll();

    Person save(Person person);

    Person findByCode(String personcode);

    boolean deleteByCode(String personcode);
}
