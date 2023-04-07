package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Skill;
import java.util.List;

public interface SkillService {

    List<Skill> getAll();

    Skill findByCode(String skillcode);

    Skill findSkill(String skillcode);

    Skill findByName(String name);

}
