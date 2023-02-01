package com.sngular.skilltree.project.fixtures;

import com.sngular.skilltree.model.*;
import com.sngular.skilltree.testutil.FileHelper;

import java.util.*;

public class ProjectFixtures {

    public static final Date date = new GregorianCalendar(2023, Calendar.JANUARY, 20).getTime();

    public static final String PROJECT_BY_CODE_JSON = FileHelper.getContent("/project/project_by_code.json");

    public static final Office OFFICE =
            Office.builder()
                    .code("itxhq")
                    .address("Baños de Arteixo S/N")
                    .phone("555 89 90 09")
                    .geolocation("45.667776, 12.455555")
                    .name("Servicios Centrales")
                    .build();
    public static final Client CLIENT_BY_CODE =
            Client.builder()
                    .code("ITX")
                    .HQ("A Coruña")
                    .country("Spain")
                    .industry("RETAIL")
                    .name("Inditex")
                    .offices(List.of(OFFICE))
                    .principalOffice("itxhq")
                    .build();

    public  static final Skill SKILL1 =
            Skill.builder()
                    .code("s1120")
                    .name("jdk11")
                    .build();

    public  static final Skill SKILL2 =
            Skill.builder()
                    .code("s1121")
                    .name("kafka")
                    .build();

    public  static final Skill SKILL3 =
            Skill.builder()
                    .code("s1122")
                    .name("db2")
                    .build();

    public  static final Skill SKILL4 =
            Skill.builder()
                    .code("s1123")
                    .name("mongodb")
                    .build();

    public  static final Skill SKILL5 =
            Skill.builder()
                    .code("s1124")
                    .name("spring")
                    .build();

    public static final List<Skill> SKILL_LIST = new ArrayList<Skill>(){{
        add(SKILL1);
        add(SKILL2);
        add(SKILL3);
        add(SKILL4);
        add(SKILL5);
    }};

    public static final ProjectRoles ROLE1 =
            ProjectRoles.builder()
                    .rol("SENIOR")
                    .number(2)
                    .build();

    public static final ProjectRoles ROLE2 =
            ProjectRoles.builder()
                    .rol("DEVELOPER")
                    .number(2)
                    .build();

    public static final ProjectRoles ROLE3 =
            ProjectRoles.builder()
                    .rol("JUNIOR")
                    .number(1)
                    .build();

    public static final List<ProjectRoles> ROLES_LIST = new ArrayList<ProjectRoles>(){{
        add(ROLE1);
        add(ROLE2);
        add(ROLE3);
    }};

    public  static final Project PROJECT_BY_CODE =
            Project.builder()
                    .code("cosmosdata")
                    .area("Logistica")
                    .tag("Project")
                    .initDate(date)
                    .desc("Data consolidation project for Data Analytics")
                    .client(CLIENT_BY_CODE)
                    .domain("Stocks")
                    .duration("Ethernal")
                    .roles(ROLES_LIST)
                    .endDate(date)
                    .guards(EnumGuards.PASSIVE)
                    .historic(List.of("Historic"))
                    .initDate(date)
                    .name("Cosmos Data")
                    .skills(SKILL_LIST)
                    .build();
}
