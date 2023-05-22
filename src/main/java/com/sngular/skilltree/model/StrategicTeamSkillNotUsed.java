package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record StrategicTeamSkillNotUsed(String teamName, List<String> skillList) {
}
