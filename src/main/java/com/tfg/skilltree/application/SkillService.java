package com.tfg.skilltree.application;

import java.util.List;

import com.tfg.skilltree.model.Skill;
import com.tfg.skilltree.model.StrategicTeamSkill;
import com.tfg.skilltree.model.StrategicTeamSkillNotUsed;
import com.tfg.skilltree.model.views.SkillStatsTittle;

public interface SkillService {

    List<Skill> getAll();

    Skill findByCode(String skillCode);

    Skill findSkill(String skillCode);

    Skill findByName(String name);

    List<StrategicTeamSkill> getStrategicSkillsUse();

    List<StrategicTeamSkillNotUsed> getNoStrategicSkillsUse();

    SkillStatsTittle getSkillStatsByTittle(String tittle);

}
