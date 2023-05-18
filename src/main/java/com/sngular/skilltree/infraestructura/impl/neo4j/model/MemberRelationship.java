package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.model.EnumCharge;
import lombok.Builder;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@Builder(toBuilder = true)
public record MemberRelationship(@RelationshipId ElementId id, @TargetNode PeopleNode peopleNode, EnumCharge charge) {
}
