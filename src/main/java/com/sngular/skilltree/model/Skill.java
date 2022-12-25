package com.sngular.skilltree.model;

import java.util.List;

public record Skill(String code, String name, List<Skill> subSkills) {
}
