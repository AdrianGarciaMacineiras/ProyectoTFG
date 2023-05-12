package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;

import com.sngular.skilltree.api.model.SkillDTO;
import com.sngular.skilltree.model.Skill;

//@Mapper(config = CommonMapperConfiguration.class)
public interface SkillMapper {

    SkillDTO toSkillDTO(Skill skill);

    Skill toSkill(SkillDTO skill);

    List<SkillDTO> toSkillsDTO (Collection<Skill> skills);

    List<Skill> toSkills (Collection<SkillDTO> skills);
}
