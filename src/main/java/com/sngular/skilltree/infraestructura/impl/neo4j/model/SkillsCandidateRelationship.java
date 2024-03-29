package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.model.EnumLevel;
import lombok.Builder;
import org.springframework.data.neo4j.core.schema.*;

@RelationshipProperties
@Builder(toBuilder = true)
public record SkillsCandidateRelationship(@RelationshipId String id, @TargetNode SkillNode skill, EnumLevel level, Integer experience) {
}
