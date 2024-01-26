package com.tfg.skilltree.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Builder;
import lombok.Value;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.text.WordUtils;
import org.springframework.util.CollectionUtils;

@Value
@Builder(toBuilder = true)
public class Skill {

    String code;

    String name;
    String namespace;

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
                newSkill = Skill.builder().name(WordUtils.capitalize(root.replace('_', ' '))).code(root).build();
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

    public static class SkillBuilder {
        private final Map<String, Skill> subSkillList = new HashMap<>();

        public SkillBuilder subSkillList(final Map<String, Skill> subSkillList) {
            if (!CollectionUtils.isEmpty(subSkillList)) {
                this.subSkillList.putAll(subSkillList);
            }
            return this;
        }

        public SkillBuilder subSkill(final Skill subSkill) {
            if (!Objects.nonNull(subSkill)) {
                this.subSkillList.put(subSkill.getCode(), subSkill);
            }
            return this;
        }

        public SkillBuilder subSkill(final String key, final Skill subSkillList) {
            if (!ObjectUtils.allNotNull(key, subSkillList)) {
                this.subSkillList.put(key, subSkillList);
            }
            return this;
        }

        public SkillBuilder clearSubSkillList() {
            this.subSkillList.clear();
            return this;
        }
    }
}
