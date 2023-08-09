package com.sngular.skilltree.infraestructura.impl.neo4j.projection;

import lombok.Builder;

@Builder
public record SkillCountProjection(String parent, String skill, Integer total) {
}
