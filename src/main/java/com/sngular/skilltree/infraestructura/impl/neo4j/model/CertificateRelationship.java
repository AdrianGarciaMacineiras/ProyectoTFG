package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Builder;
import org.springframework.data.neo4j.core.schema.*;


@RelationshipProperties
@Builder(toBuilder = true)
public record CertificateRelationship(@RelationshipId ElementId id, @TargetNode SkillNode skillNode, String comments, String date) {
}
