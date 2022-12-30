package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import com.sngular.skilltree.model.EnumLevel;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.TargetNode;

public record SkillsCandidateRelationship(String code, @TargetNode SkillNode skill, EnumLevel level, int experience) {
}
