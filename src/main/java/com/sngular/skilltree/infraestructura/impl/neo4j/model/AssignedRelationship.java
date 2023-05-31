package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Builder;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;

@RelationshipProperties
@Builder(toBuilder = true)
public record AssignedRelationship(@RelationshipId String id, @TargetNode PositionNode positionNode,
                                   LocalDate endDate, LocalDate initDate, LocalDate assignDate ,String role, Integer dedication) {
}