package com.tfg.skilltree.infraestructura.impl.neo4j.model;

import lombok.Builder;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Builder(toBuilder = true)
public record SubSkillsRelationship(@RelationshipId String id, @TargetNode SkillNode skillNode, String type) {
}
