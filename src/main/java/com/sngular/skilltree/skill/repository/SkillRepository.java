package com.sngular.skilltree.skill.repository;

import com.sngular.skilltree.skill.model.Skill;
import java.util.List;

public interface SkillRepository {

    List<Skill> findAll();

    Skill findByCode(String skillcode);
}
