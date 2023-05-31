package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;

import com.sngular.skilltree.api.model.SkillDTO;
import com.sngular.skilltree.api.model.StrategicTeamSkillDTO;
import com.sngular.skilltree.api.model.StrategicTeamSkillNotUsedDTO;
import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.model.StrategicTeamSkill;
import com.sngular.skilltree.model.StrategicTeamSkillNotUsed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = PeopleMapper.class)
public interface SkillMapper {

    SkillDTO toSkillDTO(Skill skill);

    Skill toSkill(SkillDTO skill);

    List<SkillDTO> toSkillsDTO (Collection<Skill> skills);

    List<Skill> toSkills (Collection<SkillDTO> skills);

    @Mapping(target = "teamName", source = "teamName")
    List<StrategicTeamSkillDTO> toStrategicTeamSkillDTO(List<StrategicTeamSkill> strategicTeamSkills);

    List<StrategicTeamSkillNotUsedDTO> toStrategicTeamSkillNotUsed(List<StrategicTeamSkillNotUsed> strategicTeamSkillNotUseds);
}
