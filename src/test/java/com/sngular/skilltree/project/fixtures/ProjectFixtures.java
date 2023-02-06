package com.sngular.skilltree.project.fixtures;

import com.sngular.skilltree.model.*;
import com.sngular.skilltree.testutil.FileHelper;

import java.util.*;

public class ProjectFixtures {

    public static final Date date = new GregorianCalendar(2023, Calendar.JANUARY, 20).getTime();

    public static final String PROJECT_BY_CODE_JSON = FileHelper.getContent("/project/project_by_code.json");

    public static final String UPDATED_PROJECT_BY_CODE_JSON = FileHelper.getContent("/project/updated_project_by_code.json");

    public static final String PATCH_PROJECT_BY_CODE_JSON = FileHelper.getContent("/project/patched_project_by_code.json");

    public static final String LIST_PROJECT_JSON = FileHelper.getContent("/project/list_project.json");

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

    public static final List<Skill> SKILL_LIST = List.of(SKILL1, SKILL2, SKILL3, SKILL4, SKILL5);

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

    public static final List<ProjectRoles> ROLES_LIST = List.of(ROLE1, ROLE2, ROLE3);

    public static final Project PROJECT_BY_CODE =
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

    public static final Project PROJECT2_BY_CODE =
            Project.builder()
                    .code("cosmosdata2")
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

    public static final Project UPDATED_PROJECT_BY_CODE =
            Project.builder()
                    .code("cosmosdata")
                    .area("Textil")
                    .tag("Project")
                    .initDate(date)
                    .desc("Updated description")
                    .client(CLIENT_BY_CODE)
                    .domain("Stocks")
                    .duration("1 year")
                    .roles(ROLES_LIST)
                    .endDate(date)
                    .guards(EnumGuards.PASSIVE)
                    .historic(List.of("Historic"))
                    .initDate(date)
                    .name("Cosmos Data")
                    .skills(SKILL_LIST)
                    .build();

    public static final List<Project> PROJECT_LIST = List.of(PROJECT_BY_CODE, PROJECT2_BY_CODE);
}
