package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.*;


@RelationshipProperties
public record CertificateRelationship(@RelationshipId Long id, @TargetNode SkillNode skillNode, String comments, String date) {
}
