package com.sngular.skilltree.skill.service;

import com.sngular.skilltree.skill.model.Skill;
import java.util.List;

public interface SkillService {

    List<Skill> getAll();

    Skill findByCode(String skillcode);

}
