package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import java.time.LocalDate;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import lombok.Builder;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Builder(toBuilder = true)
public record AssignedRelationship(@RelationshipId String id, @TargetNode PositionNode positionNode,
                                   @ConvertWith(converter = LocalDateConverter.class) LocalDate endDate,
                                   @ConvertWith(converter = LocalDateConverter.class) LocalDate initDate,
                                   @ConvertWith(converter = LocalDateConverter.class) LocalDate assignDate,
                                   String role, Integer dedication) {
}