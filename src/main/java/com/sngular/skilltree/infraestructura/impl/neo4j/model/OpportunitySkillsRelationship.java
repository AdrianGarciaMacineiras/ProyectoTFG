package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.*;


@RelationshipProperties
public record OpportunitySkillsRelationship(@RelationshipId Long id, @TargetNode SkillNode skill, EnumLevelReq req_level,
                                            EnumMinLevel min_level, String min_exp) {
}