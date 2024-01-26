package com.tfg.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record PositionSkill(String id, Skill skill, EnumLevelReq levelReq, EnumMinLevel minLevel, Integer minExp) {
}