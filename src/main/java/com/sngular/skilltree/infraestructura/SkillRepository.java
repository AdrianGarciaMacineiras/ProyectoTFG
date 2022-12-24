package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Skill;
import java.util.List;

public interface SkillRepository {

    List<Skill> findAll();

    Skill findByCode(String skillcode);
}
