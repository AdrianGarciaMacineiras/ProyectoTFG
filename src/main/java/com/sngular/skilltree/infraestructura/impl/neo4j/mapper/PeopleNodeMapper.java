package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.model.People;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PeopleNodeMapper {

    @InheritInverseConfiguration
    PeopleNode toNode(People People);

    People fromNode(PeopleNode peopleNode);

    List<People> map(List<PeopleNode> all);
}
