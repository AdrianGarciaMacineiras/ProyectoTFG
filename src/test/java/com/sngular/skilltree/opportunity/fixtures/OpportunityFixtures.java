package com.sngular.skilltree.opportunity.fixtures;

import com.sngular.skilltree.model.*;
import com.sngular.skilltree.testutil.FileHelper;

import java.util.*;

public class OpportunityFixtures {

    public static final Date date = new GregorianCalendar(2023, Calendar.JANUARY, 20).getTime();

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
                    .code("itx")
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
                    .code("cosmosdata")
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
                    .office("itxhq")
                    .role("Team Leader")
                    .name("Tech Leader at INDITEX")
                    .client(CLIENT_BY_CODE)
                    .closingDate(date)
                    .mode(EnumMode.REMOTE)
                    .openingDate(date)
                    .priority("HIGH")
                    .project(PROJECT_BY_CODE)
                    .skills(List.of(OPPORTUNITY_SKILL))
                    .build();

    public static final Opportunity UPDATED_OPPORTUNITY_BY_CODE =
            Opportunity.builder()
                    .code("itxtl1")
                    .office("itxhq2")
                    .role("Leader")
                    .name("Leader at INDITEX")
                    .client(CLIENT_BY_CODE)
                    .closingDate(date)
                    .mode(EnumMode.REMOTE)
                    .openingDate(date)
                    .priority("MEDIUM")
                    .project(PROJECT_BY_CODE)
                    .skills(List.of(OPPORTUNITY_SKILL))
                    .build();

    public static final Opportunity OPPORTUNITY2_BY_CODE =
            Opportunity.builder()
                    .code("itxtl2")
                    .office("itxhq")
                    .role("Team Leader")
                    .name("Tech Leader at INDITEX")
                    .client(CLIENT_BY_CODE)
                    .closingDate(date)
                    .mode(EnumMode.REMOTE)
                    .openingDate(date)
                    .priority("HIGH")
                    .project(PROJECT_BY_CODE)
                    .skills(List.of(OPPORTUNITY_SKILL))
                    .build();

    public static final List<Opportunity> OPPORTUNITY_LIST = List.of(OPPORTUNITY_BY_CODE, OPPORTUNITY2_BY_CODE);
}