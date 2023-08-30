package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import java.time.LocalDateTime;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateTimeConverter;
import lombok.Builder;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;


@RelationshipProperties
@Builder(toBuilder = true)
public record CertificateRelationship(@RelationshipId String id, @TargetNode SkillNode skillNode, String comment, @ConvertWith(converter = LocalDateTimeConverter.class) LocalDateTime date) {
}
