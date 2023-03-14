package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record SkillsCandidate(String code, EnumLevel level, Integer experience) {
}