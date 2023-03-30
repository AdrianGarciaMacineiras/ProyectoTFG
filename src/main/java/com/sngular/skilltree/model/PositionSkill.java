package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record PositionSkill(Skill skill, EnumLevelReq levelReq, EnumMinLevel minLevel, Integer minExp) {
}