package com.sngular.skilltree.skill.fixtures;

import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.testutil.FileHelper;

import java.util.List;

public class SkillFixtures {

    public static final String SKILL_BY_CODE_JSON = FileHelper.getContent("/skill/skill_by_code.json");

    public static final String LIST_SKILL_JSON = FileHelper.getContent("/skill/list_skill.json");

    public  static final Skill SKILL2_BY_CODE =
            Skill.builder()
                    .code("s1121")
                    .name("Kafka")
                    .build();

    public  static final Skill SKILL_BY_CODE =
            Skill.builder()
                    .code("s1120")
                    .name("Spring")
                    .subSkills(List.of(SKILL2_BY_CODE))
                    .build();

    public  static final Skill SKILL3_BY_CODE =
            Skill.builder()
                    .code("s1122")
                    .name("Mongodb")
                    .build();

    public static final List<Skill> SKILL_LIST = List.of(SKILL_BY_CODE, SKILL3_BY_CODE);

}
