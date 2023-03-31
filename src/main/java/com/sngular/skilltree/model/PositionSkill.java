package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record PositionSkill(Long id, Skill skill, EnumLevelReq levelReq, EnumMinLevel minLevel, Integer minExp) {
}