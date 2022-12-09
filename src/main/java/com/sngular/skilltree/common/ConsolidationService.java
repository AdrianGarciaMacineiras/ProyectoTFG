package com.sngular.skilltree.common;

import com.sngular.skilltree.person.model.Person;
import org.springframework.stereotype.Component;

public interface ConsolidationService {
  Person findPerson(String personCode);

  String encodePerson(Person person);
}
