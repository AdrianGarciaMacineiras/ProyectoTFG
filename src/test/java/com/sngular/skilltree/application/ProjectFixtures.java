package com.sngular.skilltree.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.sngular.skilltree.model.Client;
import com.sngular.skilltree.model.EnumGuards;
import com.sngular.skilltree.model.Office;
import com.sngular.skilltree.model.Project;
import com.sngular.skilltree.model.ProjectRoles;
import com.sngular.skilltree.model.Skill;

public class ProjectFixtures {

    public static LocalDate date = LocalDate.parse("20-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

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
                    .code("1")
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
                    .code("1")
                    .area("Logistica")
                    .tag("Project")
                    .initDate(date)
                    .desc("Data consolidation project for Data Analytics")
                    .client(CLIENT_BY_CODE)
                    .domain("Stocks")
                    .duration("Ethernal")
                    .endDate(date)
                    .guards(EnumGuards.PASSIVE)
                    .historic(List.of("Historic"))
                    .initDate(date)
                    .name("Cosmos Data")
                    .skills(SKILL_LIST)
                    .build();

    public static final Project PROJECT2_BY_CODE =
            Project.builder()
                    .code("2")
                    .area("Logistica")
                    .tag("Project")
                    .initDate(date)
                    .desc("Data consolidation project for Data Analytics")
                    .client(CLIENT_BY_CODE)
                    .domain("Stocks")
                    .duration("Ethernal")
                    .endDate(date)
                    .guards(EnumGuards.PASSIVE)
                    .historic(List.of("Historic"))
                    .initDate(date)
                    .name("Cosmos Data")
                    .skills(SKILL_LIST)
                    .build();

    public static final Project PROJECT_BY_CODE_DELETED_TRUE =
            Project.builder()
                    .code("1")
                    .area("Logistica")
                    .tag("Project")
                    .initDate(date)
                    .desc("Data consolidation project for Data Analytics")
                    .client(CLIENT_BY_CODE)
                    .domain("Stocks")
                    .duration("Ethernal")
                    .endDate(date)
                    .guards(EnumGuards.PASSIVE)
                    .historic(List.of("Historic"))
                    .initDate(date)
                    .name("Cosmos Data")
                    .skills(SKILL_LIST)
                    .deleted(true)
                    .build();

    public static final List<Project> PROJECT_LIST = List.of(PROJECT_BY_CODE, PROJECT2_BY_CODE);

}
