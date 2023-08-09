package com.sngular.skilltree.model.views;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record SkillStatsTittle(String parent, String name, Integer total, List<SkillStatsTittle> subSkills) {
}
