package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.EnumLevelReqConverter;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.EnumMinLevelConverter;
import lombok.Builder;
import org.springframework.data.neo4j.core.convert.ConvertWith;
import org.springframework.data.neo4j.core.schema.RelationshipId;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;


@RelationshipProperties
@Builder(toBuilder = true)
public record PositionSkillsRelationship(@RelationshipId String id, @TargetNode SkillNode skill,
                                         @ConvertWith(converter = EnumLevelReqConverter.class) EnumLevelReq req_level,
                                         @ConvertWith(converter = EnumMinLevelConverter.class) EnumMinLevel min_level,
                                         Integer min_exp) {
}