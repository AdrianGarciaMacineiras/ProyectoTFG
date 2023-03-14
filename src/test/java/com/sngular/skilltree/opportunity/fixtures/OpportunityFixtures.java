package com.sngular.skilltree.opportunity.fixtures;

import com.sngular.skilltree.model.*;
import com.sngular.skilltree.testutil.FileHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OpportunityFixtures {

    public static LocalDate date = LocalDate.parse("20-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    public static final String OPPORTUNITY_BY_CODE_JSON = FileHelper.getContent("/opportunity/opportunity_by_code.json");

    public static final String UPDATED_OPPORTUNITY_BY_CODE_JSON = FileHelper.getContent("/opportunity/updated_opportunity_by_code.json");

    public static final String PATCH_OPPORTUNITY_BY_CODE_JSON = FileHelper.getContent("/opportunity/patched_opportunity.json");

    public static final String LIST_OPPORTUNITY_JSON = FileHelper.getContent("/opportunity/list_opportunity.json");

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
                    .roles(List.of(ROLES))
                    .endDate(date)
                    .guards(EnumGuards.PASSIVE)
                    .historic(List.of("Historic"))
                    .initDate(date)
                    .name("Cosmos Data")
                    .skills(List.of(SKILL_BY_CODE))
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

    public static final OpportunitySkill OPPORTUNITY_SKILL =
            OpportunitySkill.builder()
                    .skill(SKILL_BY_CODE)
                    .levelReq(EnumLevelReq.MANDATORY)
                    .minExp(7)
                    .minLevel(EnumMinLevel.HIGH)
                    .build();

    public static final Opportunity OPPORTUNITY_BY_CODE =
            Opportunity.builder()
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

    public static final Opportunity UPDATED_OPPORTUNITY_BY_CODE =
            Opportunity.builder()
                    .code("itxtl1")
                    .office(OFFICE)
                    .role("Leader")
                    .name("Leader at INDITEX")
                    .client(CLIENT_BY_CODE)
                    .closingDate(date)
                    .mode(EnumMode.REMOTE)
                    .openingDate(date)
                    .priority("MEDIUM")
                    .project(PROJECT_BY_CODE)
                    .managedBy(PEOPLE_BY_CODE)
                    .skills(List.of(OPPORTUNITY_SKILL))
                    .build();

    public static final Opportunity OPPORTUNITY2_BY_CODE =
            Opportunity.builder()
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

    public static final List<Opportunity> OPPORTUNITY_LIST = List.of(OPPORTUNITY_BY_CODE, OPPORTUNITY2_BY_CODE);
}