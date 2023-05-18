package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Builder;
import org.springframework.data.neo4j.core.schema.*;


@RelationshipProperties
@Builder(toBuilder = true)
public record PositionSkillsRelationship(@RelationshipId ElementId id, @TargetNode SkillNode skill, EnumLevelReq req_level,
                                         EnumMinLevel min_level, String min_exp) {
}