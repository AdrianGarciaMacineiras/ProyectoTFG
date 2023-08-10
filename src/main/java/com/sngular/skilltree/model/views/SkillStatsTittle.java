package com.sngular.skilltree.model.views;

import lombok.Builder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder(toBuilder = true)
public record SkillStatsTittle(String parent, String name, Integer total, Map<String, SkillStatsTittle> subSkills) {

    public SkillStatsTittle {
        subSkills = new HashMap<>();
    }

    public void addStat(final String parent, final SkillStatsTittle stat) {
        var root = parent;
        if (parent.contains(".")) {
            root = parent.substring(0, stat.parent.indexOf('.'));
        }
        if (name.equalsIgnoreCase(root)) {
            subSkills.put(stat.name, stat);
        } else {
            final SkillStatsTittle newSkill;
            if (subSkills.containsKey(root)) {
                newSkill = subSkills.get(root);
            } else {
                newSkill = SkillStatsTittle.builder().name(root).total(stat.total()).build();
                subSkills.put(root, newSkill);
            }
            if (!parent.contains(".")) {
                newSkill.addStat(parent, stat);
            } else {
                newSkill.addStat(parent.substring(parent.indexOf('.') + 1), stat);
            }
        }
    }

    public List<SkillStatsTittle> subSkillStats() {
        return new ArrayList<>(subSkills.values());
    }
}
