package com.sngular.skilltree.person.repository.impl.neo4j.mapper;

import com.sngular.skilltree.person.repository.impl.neo4j.model.PersonNode;
import com.sngular.skilltree.person.model.Person;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PersonNodeMapper {

    @InheritInverseConfiguration
    PersonNode toNode(Person Person);

    Person fromNode(PersonNode personNode);

    List<Person> map(List<PersonNode> all);
}
