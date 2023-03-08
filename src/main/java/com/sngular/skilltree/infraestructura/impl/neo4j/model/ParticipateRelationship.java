package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;
import java.util.Date;


import java.util.List;

@RelationshipProperties
public record ParticipateRelationship(@RelationshipId Long id, @TargetNode ProjectNode project,
                                      LocalDate endDate, LocalDate initDate, String role) {
}