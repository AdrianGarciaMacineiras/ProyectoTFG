package com.tfg.skilltree.infraestructura.impl.neo4j.model;

import java.time.LocalDate;

import com.tfg.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import lombok.Builder;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Builder(toBuilder = true)
public record CandidateRelationship(@RelationshipId String id, String code, EnumStatus status,
                                    @ConvertWith(converter = LocalDateConverter.class) LocalDate introductionDate,
                                    @ConvertWith(converter = LocalDateConverter.class) LocalDate resolutionDate,
                                    @ConvertWith(converter = LocalDateConverter.class) LocalDate creationDate,
                                    @ConvertWith(converter = LocalDateConverter.class) LocalDate interviewDate,
                                    @TargetNode PeopleNode candidate) {
}
