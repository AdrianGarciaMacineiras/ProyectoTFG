package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.model.EnumStatus;
import lombok.Builder;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

import java.time.LocalDate;

@RelationshipProperties
@Builder(toBuilder = true)
public record CandidateRelationship(@RelationshipId String id, String code, EnumStatus status, LocalDate introductionDate,
                                    LocalDate resolutionDate, LocalDate creationDate, LocalDate interviewDate, @TargetNode PeopleNode candidate) {
}
