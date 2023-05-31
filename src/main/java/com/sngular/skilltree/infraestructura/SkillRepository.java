package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.model.StrategicTeamSkill;
import com.sngular.skilltree.model.StrategicTeamSkillNotUsed;

import java.util.List;

public interface SkillRepository {

    List<Skill> findAll();

    Skill findByCode(String skillcode);

    Skill findSkill(String skillcode);

    Skill findByName(String skillname);

    List<StrategicTeamSkill> getStrategicSkillsUse();

    List<StrategicTeamSkillNotUsed> getNoStrategicSkillsUse();

}
