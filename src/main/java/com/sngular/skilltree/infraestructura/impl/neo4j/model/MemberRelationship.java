package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.model.EnumCharge;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public record MemberRelationship(@RelationshipId Long id, @TargetNode PeopleNode peopleNode, EnumCharge charge) {
}
