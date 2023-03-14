package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record OpportunitySkill(Skill skill, EnumLevelReq levelReq, EnumMinLevel minLevel, Integer minExp) {
}