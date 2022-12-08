package com.sngular.skilltree.skill.model;

import java.util.List;

public record Skill(String code, String nombre, List<Skill> subSkills) {
}
