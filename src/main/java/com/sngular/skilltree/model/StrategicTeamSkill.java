package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record StrategicTeamSkill(String teamName, List<StrategicUse> skillList) {
}
