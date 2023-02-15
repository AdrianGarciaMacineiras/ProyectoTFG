package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.util.Date;


@RelationshipProperties
public record EvolutionRelationship(@TargetNode Roles role, String title, String category, Date fromDate) {
};