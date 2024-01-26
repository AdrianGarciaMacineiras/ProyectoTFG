package com.tfg.skilltree.application;

import com.tfg.skilltree.model.Skill;

import java.util.List;
import java.util.Map;

public class SkillFixtures {

    public static final Skill SKILL2_BY_CODE =
      Skill.builder()
           .code("s1121")
           .name("Kafka")
           .build();

    public static final Skill SKILL_BY_CODE =
      Skill.builder()
           .code("s1120")
           .name("Spring")
              .subSkillList(Map.of(SKILL2_BY_CODE.getName(), SKILL2_BY_CODE))
           .build();

    public static final Skill SKILL3_BY_CODE =
      Skill.builder()
                    .code("s1122")
                    .name("Mongodb")
                    .build();

    public static final List<Skill> SKILL_LIST = List.of(SKILL_BY_CODE, SKILL3_BY_CODE);

}
