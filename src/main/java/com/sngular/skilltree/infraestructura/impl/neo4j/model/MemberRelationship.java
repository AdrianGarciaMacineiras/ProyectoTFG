package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public record MemberRelationship(@RelationshipId Long id, @TargetNode PeopleNode people, EnumPosition position) {
}
