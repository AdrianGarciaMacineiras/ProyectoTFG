package com.sngular.skilltree.person.repository.impl.neo4j;

import com.sngular.skilltree.person.model.Person;
import com.sngular.skilltree.person.repository.PersonRepository;
import com.sngular.skilltree.person.repository.impl.neo4j.mapper.PersonNodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    private final PersonCrudRepository crud;

    private final PersonNodeMapper mapper;

    @Override
    public List<Person> findAll() {
        return mapper.map(crud.findAll());
    }

    @Override
    public Person save(Person person) {
        return mapper.fromNode(crud.save(mapper.toNode(person)));
    }

    @Override
    public Person findByCode(String personcode) {
        return mapper.fromNode(crud.findByCode(personcode));
    }

    @Override
    public boolean deleteByCode(String personcode) {
        var node = crud.findByCode(personcode);
        crud.delete(node);
        return true;
    }
}
