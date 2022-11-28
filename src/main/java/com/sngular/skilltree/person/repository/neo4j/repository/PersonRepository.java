package com.sngular.skilltree.person.repository.neo4j.repository;

import com.sngular.skilltree.person.model.Person;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface PersonRepository extends ReactiveNeo4jRepository<Person, String> {
}
