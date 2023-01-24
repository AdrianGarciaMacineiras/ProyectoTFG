package com.sngular.skilltree.model;

import lombok.Builder;

@Builder
public record SkillsCandidate(String code, EnumLevel level, int experience) {
}