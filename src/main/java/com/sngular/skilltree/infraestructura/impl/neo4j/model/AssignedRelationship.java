package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;

@RelationshipProperties
public record AssignedRelationship(@RelationshipId Long id, @TargetNode PositionNode positionNode,
                                   LocalDate endDate, LocalDate initDate, LocalDate assignedDate, String role) {
}