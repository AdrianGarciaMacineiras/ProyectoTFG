package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record PuestoSkill(Skill skill, EnumLevelReq levelReq, EnumMinLevel minLevel, Integer minExp) {
}