package com.sngular.skilltree.common.impl;

import com.sngular.skilltree.common.ConsolidationService;
import com.sngular.skilltree.person.model.Person;
import com.sngular.skilltree.person.repository.PersonRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsolidationServiceImpl implements ConsolidationService {

  private final PersonRepository personRepository;

  @Override
  public Person findPerson(String personCode) {
    return personRepository.findByCode(personCode);
  }

  @Override
  public String encodePerson(Person person) {
    return person.code();
  }
}
