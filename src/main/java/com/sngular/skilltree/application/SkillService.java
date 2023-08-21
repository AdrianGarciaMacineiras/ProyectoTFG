package com.sngular.skilltree.application;

import java.util.List;

import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.model.StrategicTeamSkill;
import com.sngular.skilltree.model.StrategicTeamSkillNotUsed;
import com.sngular.skilltree.model.views.SkillStatsTittle;

public interface SkillService {

    List<Skill> getAll();

    Skill findByCode(String skillCode);

    Skill findSkill(String skillCode);

    Skill findByName(String name);

    List<StrategicTeamSkill> getStrategicSkillsUse();

    List<StrategicTeamSkillNotUsed> getNoStrategicSkillsUse();

    SkillStatsTittle getSkillStatsByTittle(String tittle);

}
