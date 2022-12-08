package com.sngular.skilltree.skill.repository.impl.neo4j.model;


import java.util.List;

public record SkillNode(String code, String nombre, List<SkillNode> subSkills) {
}