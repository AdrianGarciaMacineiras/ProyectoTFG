package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;
//TODO LISTA DE PERSONAS
@Builder(toBuilder = true)
public record StrategicTeamSkill(String teamName, List<People> peopleList) {
}
