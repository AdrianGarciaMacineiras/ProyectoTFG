package com.tfg.skilltree.model;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record StrategicTeamSkill(String teamName, List<People> peopleList, List<String> skillList) {
}
