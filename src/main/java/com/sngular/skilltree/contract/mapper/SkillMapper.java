package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.SkillDTO;
import com.sngular.skilltree.model.Skill;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface SkillMapper {

    SkillDTO toSkillDTO(Skill skill);

    Skill toSkill(SkillDTO skill);

    List<SkillDTO> toSkillsDTO (Collection<Skill> skills);

    List<Skill> toSkills (Collection<SkillDTO> skills);
}
