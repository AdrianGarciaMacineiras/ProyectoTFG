package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record Skill(String code, String name, List<Skill> subSkills) {
}
