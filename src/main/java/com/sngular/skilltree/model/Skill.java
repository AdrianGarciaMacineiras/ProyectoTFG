package com.sngular.skilltree.model;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Value
@Slf4j
@Builder(toBuilder = true)
public class Skill {

    String code;

    String name;

    @Singular("subSkill")
    Map<String, Skill> subSkillList;

    public void addSkill(final String parent, final Skill skill) {
        var root = parent;
        if (parent.contains(".")) {
            root = parent.substring(0, parent.indexOf('.'));
        }
        if (!name.equalsIgnoreCase(WordUtils.capitalize(root.replace('_', ' ')))) {
            final Skill newSkill;
            if (subSkillList.containsKey(root)) {
                newSkill = subSkillList.get(root);
            } else {
                newSkill = Skill.builder().name(WordUtils.capitalize(root.replace('_', ' '))).code(skill.getCode()).build();
                subSkillList.put(root, newSkill);
            }
            if (!parent.contains(".")) {
                newSkill.addSkill(parent, skill);
            } else {
                newSkill.addSkill(parent.substring(parent.indexOf('.') + 1), skill);
            }
        }
    }

    public List<Skill> subSkills() {
        return new ArrayList<>(subSkillList.values());
    }
}
