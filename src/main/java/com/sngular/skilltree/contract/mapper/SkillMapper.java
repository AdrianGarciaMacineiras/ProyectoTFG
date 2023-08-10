package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.SkillDTO;
import com.sngular.skilltree.api.model.SkillStatDTO;
import com.sngular.skilltree.api.model.StrategicTeamSkillDTO;
import com.sngular.skilltree.api.model.StrategicTeamSkillNotUsedDTO;
import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.model.StrategicTeamSkill;
import com.sngular.skilltree.model.StrategicTeamSkillNotUsed;
import com.sngular.skilltree.model.views.SkillStatsTittle;
import org.apache.commons.lang3.ObjectUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;
import java.util.List;

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
