package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.model.EnumStatus;
import lombok.Builder;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;

@RelationshipProperties
@Builder(toBuilder = true)
public record CandidateRelationship(@RelationshipId Long id, String code, EnumStatus status, LocalDate introductionDate,
                                    LocalDate resolutionDate, LocalDate creationDate, LocalDate interviewDate, @TargetNode PeopleNode candidate) {
}
