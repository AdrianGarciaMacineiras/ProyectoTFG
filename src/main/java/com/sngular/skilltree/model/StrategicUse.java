package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record StrategicUse(String skillName, List<People> peopleList) {
}
