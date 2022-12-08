package com.sngular.skilltree.person.service;

import com.sngular.skilltree.person.mapper.PersonMapper;
import com.sngular.skilltree.person.model.Person;
import com.sngular.skilltree.person.repository.PersonRepository;
import com.sngular.skilltree.project.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService{

    private final PersonRepository personRepository;

    private final PersonMapper mapper;

    @Override
    public List<Person> getAll() {
        return personRepository.findAll();
    }

    @Override
    public Person create(Person person) {
        validate(person);
        return personRepository.save(person);
    }

    @Override
    public Person findByCode(String personcode) {
        return personRepository.findByCode(personcode);
    }

    @Override
    public boolean deleteByCode(String personcode) {
        return personRepository.deleteByCode(personcode);
    }

    @Override
    public Person update(String personcode, Person newPerson) {
        var oldperson = personRepository.findByCode(personcode);
        mapper.update(oldperson, newPerson);
        return personRepository.save(oldperson);
    }

    @Override
    public Person patch(String personcode, Person patchedPerson) {
        var oldperson = personRepository.findByCode(personcode);
        mapper.update(oldperson, patchedPerson);
        return personRepository.save(oldperson);    }

    private void validate(Person person) {
    }
}
