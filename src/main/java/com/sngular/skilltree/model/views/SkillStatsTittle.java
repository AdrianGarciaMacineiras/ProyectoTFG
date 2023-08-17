package com.sngular.skilltree.model.views;

import lombok.Builder;
import org.apache.commons.text.WordUtils;

import java.util.*;

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
            this.subSkills.put(stat.name(), stat);
        }
    }

    public List<SkillStatsTittle> subSkillStats() {
        return new ArrayList<>(subSkills.values());
    }
}
