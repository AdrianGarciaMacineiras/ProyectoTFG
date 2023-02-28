package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.*;

import java.util.Date;


import java.util.List;

@RelationshipProperties
public record ParticipateRelationship(@RelationshipId Long id, @TargetNode ProjectNode project,
                                      String endDate, String initDate, String role) {
}