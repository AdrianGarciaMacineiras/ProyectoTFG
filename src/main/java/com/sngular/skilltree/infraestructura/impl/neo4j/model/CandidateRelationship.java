package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.model.EnumStatus;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;

@RelationshipProperties
public record CandidateRelationship(@RelationshipId Long id, String code, EnumStatus status, LocalDate introductionDate,
                                    LocalDate resolutionDate, LocalDate creationDate, @TargetNode PeopleNode candidate) {
}
