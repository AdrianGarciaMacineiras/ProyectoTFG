package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import lombok.Builder;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;


@RelationshipProperties
@Builder(toBuilder = true)
public record CertificateRelationship(@RelationshipId String id, @TargetNode SkillNode skillNode, String comments, @ConvertWith(converter = LocalDateConverter.class) LocalDate date) {
}
