package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateTimeConverter;
import lombok.Builder;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@RelationshipProperties
@Builder(toBuilder = true)
public record CertificateRelationship(@RelationshipId String id, @TargetNode SkillNode skillNode, String comment, @ConvertWith(converter = LocalDateTimeConverter.class) LocalDateTime date) {
}
