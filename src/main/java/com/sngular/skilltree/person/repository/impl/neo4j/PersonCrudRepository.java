package com.sngular.skilltree.person.repository.impl.neo4j;

import com.sngular.skilltree.person.repository.impl.neo4j.model.PersonNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PersonCrudRepository extends Neo4jRepository<PersonNode, String> {

    PersonNode findByCode(String personcode);
}
