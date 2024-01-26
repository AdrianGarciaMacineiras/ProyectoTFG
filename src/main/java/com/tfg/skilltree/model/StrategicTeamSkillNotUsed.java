package com.tfg.skilltree.model;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record StrategicTeamSkillNotUsed(String teamName, List<String> skillList) {
}
