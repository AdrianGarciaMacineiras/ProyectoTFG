package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public record KnowsRelationship(@RelationshipId Long id, String experience, String level, String primary, @TargetNode SkillNode skillNode) {
};
