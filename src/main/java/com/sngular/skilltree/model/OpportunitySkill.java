package com.sngular.skilltree.model;

import lombok.Builder;

@Builder
public record OpportunitySkill(Skill skill, EnumLevelReq levelReq, EnumMinLevel minLevel, int minExp) {
}