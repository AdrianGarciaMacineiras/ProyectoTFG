package com.sngular.skilltree.skill.mapper;

import com.sngular.skilltree.skill.model.Skill;
import java.util.Collection;
import java.util.List;

public interface SkillMapper {

    SkillDTO toSkillDTO(Skill skill);

    List<SkillDTO> toSkillsDTO (Collection<Skill> skills);
}
