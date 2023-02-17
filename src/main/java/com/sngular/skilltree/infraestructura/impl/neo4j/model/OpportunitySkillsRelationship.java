package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;


@RelationshipProperties
public record OpportunitySkillsRelationship(@Id @GeneratedValue Long id, @TargetNode SkillNode skill, EnumLevelReq levelReq, EnumMinLevel minLevel, Integer minExp) {
}