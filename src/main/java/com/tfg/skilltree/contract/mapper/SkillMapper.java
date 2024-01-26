package com.tfg.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;

import com.tfg.skilltree.api.model.SkillDTO;
import com.tfg.skilltree.api.model.SkillStatDTO;
import com.tfg.skilltree.api.model.StrategicTeamSkillDTO;
import com.tfg.skilltree.api.model.StrategicTeamSkillNotUsedDTO;
import com.tfg.skilltree.common.config.CommonMapperConfiguration;
import com.tfg.skilltree.model.Skill;
import com.tfg.skilltree.model.StrategicTeamSkill;
import com.tfg.skilltree.model.StrategicTeamSkillNotUsed;
import com.tfg.skilltree.model.views.SkillStatsTittle;
import org.apache.commons.lang3.ObjectUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = PeopleMapper.class)
public interface SkillMapper {

    default SkillDTO toSkillDTO(Skill skill) {
        return SkillDTO.builder().name(skill.getName()).code(skill.getCode()).subSkills(toSkillsDTO(skill.subSkills())).build();
    }

    Skill toSkill(SkillDTO skill);

    List<SkillDTO> toSkillsDTO(List<Skill> skills);

    List<Skill> toSkills (Collection<SkillDTO> skills);

    @Mapping(target = "teamName", source = "teamName")
    List<StrategicTeamSkillDTO> toStrategicTeamSkillDTO(List<StrategicTeamSkill> strategicTeamSkills);

    List<StrategicTeamSkillNotUsedDTO> toStrategicTeamSkillNotUsed(List<StrategicTeamSkillNotUsed> strategicTeamSkillNotUseds);

    List<SkillStatDTO> toSkillStatList(List<SkillStatsTittle> skillStatsByTittle);

    default SkillStatDTO toSkillStat(SkillStatsTittle skillStatsTittle) {
        return
                SkillStatDTO
                        .builder()
                        .subSkills(toSkillStatList(skillStatsTittle.subSkillStats()))
                        .name(skillStatsTittle.name())
                        .total(ObjectUtils.defaultIfNull(skillStatsTittle.total(), 0))
                        .build();
    }
}
