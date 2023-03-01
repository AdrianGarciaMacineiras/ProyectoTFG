package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.model.EnumLevel;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
public record SkillsCandidateRelationship(@RelationshipId Long id, @TargetNode SkillNode skill, EnumLevel level, String experience) {
}
