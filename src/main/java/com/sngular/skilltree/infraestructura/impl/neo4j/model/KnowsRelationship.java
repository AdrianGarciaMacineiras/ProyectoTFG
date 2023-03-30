package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public record KnowsRelationship(@RelationshipId Long id, Integer experience, EnumLevel level, Boolean primary, @TargetNode SkillNode skillNode) {
};
