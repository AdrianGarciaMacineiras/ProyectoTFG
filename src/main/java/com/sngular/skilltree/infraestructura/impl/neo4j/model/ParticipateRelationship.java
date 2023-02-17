package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;
import java.util.Date;


import java.util.List;

@RelationshipProperties
public record ParticipateRelationship(@Id @GeneratedValue Long id, @TargetNode ProjectNode project,
                                      Date endDate, Date initDate, String role) {
}