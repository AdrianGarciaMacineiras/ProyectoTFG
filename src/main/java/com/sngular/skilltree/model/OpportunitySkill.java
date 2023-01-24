package com.sngular.skilltree.model;

import lombok.Builder;

@Builder
public record OpportunitySkill(Skill skill, EnumLevelReq enumLevelReq, EnumMinLevel enumMinLevel, int minExp) {
}