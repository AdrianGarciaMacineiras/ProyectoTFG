package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Skill(String code, String name, List<Skill> subSkills) {
}
