package com.tfg.skilltree.model.views;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.Builder;
import org.apache.commons.text.WordUtils;

@Builder(toBuilder = true)
public record SkillStatsTittle(String parent, String name, Integer total, Map<String, SkillStatsTittle> subSkills) {

    public SkillStatsTittle {
        subSkills = new HashMap<>();
    }

    public void addStat(final String parent, final SkillStatsTittle stat) {
        var root = parent;
        if (Objects.nonNull(parent)) {
            if (parent.contains(".")) {
                root = parent.substring(0, parent.indexOf('.'));
            }
            if (!name.equalsIgnoreCase(root)) {
                final SkillStatsTittle newSkill;
                if (subSkills.containsKey(root)) {
                    newSkill = subSkills.get(root);
                } else {
                    newSkill = SkillStatsTittle.builder().name(WordUtils.capitalize(root.replace('_', ' '))).total(stat.total()).build();
                    subSkills.put(root, newSkill);
                }
                if (!parent.contains(".")) {
                    newSkill.addStat(null, stat);
                } else {
                    newSkill.addStat(parent.substring(parent.indexOf('.') + 1), stat);
                }
            }
        } else {
            this.subSkills.put(WordUtils.capitalize(stat.name().replace('_', ' ')), stat);
        }
    }

    public List<SkillStatsTittle> subSkillStats() {
        return new ArrayList<>(subSkills.values());
    }
}
