package com.tfg.skilltree.infraestructura;

import java.util.List;

import com.tfg.skilltree.model.Skill;
import com.tfg.skilltree.model.StrategicTeamSkill;
import com.tfg.skilltree.model.StrategicTeamSkillNotUsed;
import com.tfg.skilltree.model.views.SkillStatsTittle;

public interface SkillRepository {

    List<Skill> findAll();

    Skill findByCode(String skillcode);

    Skill findSkill(String skillcode);

    Skill findByName(String skillname);

    List<StrategicTeamSkill> getStrategicSkillsUse();

    List<StrategicTeamSkillNotUsed> getNoStrategicSkillsUse();

    SkillStatsTittle getSkillStatsByTittle(String tittle);

}
