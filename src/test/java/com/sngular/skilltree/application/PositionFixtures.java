package com.sngular.skilltree.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.sngular.skilltree.model.Client;
import com.sngular.skilltree.model.EnumGuards;
import com.sngular.skilltree.model.EnumLevelReq;
import com.sngular.skilltree.model.EnumMinLevel;
import com.sngular.skilltree.model.EnumMode;
import com.sngular.skilltree.model.EnumTitle;
import com.sngular.skilltree.model.Office;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.model.PositionSkill;
import com.sngular.skilltree.model.Project;
import com.sngular.skilltree.model.ProjectRoles;
import com.sngular.skilltree.model.Skill;

public class PositionFixtures {

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
                    .code(1L)
                    .HQ("A Coruña")
                    .country("Spain")
                    .industry("RETAIL")
                    .name("Inditex")
                    .offices(List.of(OFFICE))
                    .principalOffice("itxhq")
                    .build();

    public  static final Skill SKILL_BY_CODE =
            Skill.builder()
                    .code("s1120")
                    .name("Spring")
                    .build();

    public static final ProjectRoles ROLES =
            ProjectRoles.builder()
                    .rol("senior")
                    .number(2)
                    .build();

    public  static final Project PROJECT_BY_CODE =
            Project.builder()
                    .code(1L)
                    .area("Logistica")
                    .desc("Data consolidation project for Data Analytics")
                    .client(CLIENT_BY_CODE)
                    .domain("Stocks")
                    .duration("Ethernal")
                    .endDate(date)
                    .guards(EnumGuards.PASSIVE)
                    .historic(List.of("Historic"))
                    .initDate(date)
                    .name("Cosmos Data")
                    .skills(List.of(SKILL_BY_CODE))
                    .build();

    public static final PositionSkill OPPORTUNITY_SKILL =
            PositionSkill.builder()
                    .skill(SKILL_BY_CODE)
                    .levelReq(EnumLevelReq.MANDATORY)
                    .minExp(7)
                    .minLevel(EnumMinLevel.HIGH)
                    .build();

    public static final People PEOPLE_BY_CODE =
            People.builder()
                    .code(1L)
                    .name("people")
                    .surname("surname")
                    .employeeId("employeeId")
                    .title(EnumTitle.SENIOR)
                    .birthDate(date)
                    .build();

    public static final Position POSITION_BY_CODE =
            Position.builder()
                    .code("itxtl1")
                    .office(OFFICE)
                    .role("Team Leader")
                    .name("Tech Leader at INDITEX")
                    .client(CLIENT_BY_CODE)
                    .closingDate(date)
                    .mode(EnumMode.REMOTE)
                    .openingDate(date)
                    .priority("HIGH")
                    .project(PROJECT_BY_CODE)
                    .managedBy(PEOPLE_BY_CODE)
                    .skills(List.of(OPPORTUNITY_SKILL))
                    .build();

    public static final Position POSITION_2_BY_CODE =
            Position.builder()
                    .code("itxtl2")
                    .office(OFFICE)
                    .role("Team Leader")
                    .name("Tech Leader at INDITEX")
                    .client(CLIENT_BY_CODE)
                    .closingDate(date)
                    .mode(EnumMode.REMOTE)
                    .openingDate(date)
                    .priority("HIGH")
                    .project(PROJECT_BY_CODE)
                    .managedBy(PEOPLE_BY_CODE)
                    .skills(List.of(OPPORTUNITY_SKILL))
                    .build();

    public static final List<Position> POSITION_LIST = new ArrayList<Position>(){{
       add(POSITION_BY_CODE);
       add(POSITION_2_BY_CODE);
    }};
}
