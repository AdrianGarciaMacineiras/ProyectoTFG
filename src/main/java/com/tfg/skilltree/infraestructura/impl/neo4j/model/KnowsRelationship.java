package com.tfg.skilltree.infraestructura.impl.neo4j.model;

import com.tfg.skilltree.infraestructura.impl.neo4j.model.converter.EnumLevelConverter;
import lombok.Builder;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Builder(toBuilder = true)
public record KnowsRelationship(@RelationshipId String id, Integer experience,
                                @ConvertWith(converter = EnumLevelConverter.class) EnumLevel level,
                                Boolean primary,
                                @TargetNode SkillNode skill) {
}
