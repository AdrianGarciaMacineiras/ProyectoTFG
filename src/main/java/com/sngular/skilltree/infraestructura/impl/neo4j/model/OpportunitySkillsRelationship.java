package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;


@RelationshipProperties
public record OpportunitySkillsRelationship(@Id @GeneratedValue String code, @TargetNode SkillNode skill, EnumLevelReq enumLevelReq, EnumMinLevel enumMinLevel, int minExp) {
}