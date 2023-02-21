package com.sngular.skilltree.candidate.fixtures;


import com.sngular.skilltree.model.*;
import com.sngular.skilltree.testutil.FileHelper;

import java.util.*;

public class CandidateFixtures {

    public static final Date date = new GregorianCalendar(2023, Calendar.JANUARY, 20).getTime();

    public static final String CANDIDATE_BY_CODE_JSON = FileHelper.getContent("/candidate/candidate_by_code.json");

    public static final String UPDATED_CANDIDATE_BY_CODE_JSON = FileHelper.getContent("/candidate/updated_candidate_by_code.json");

    public static final String PATCH_CANDIDATE_BY_CODE_JSON = FileHelper.getContent("/candidate/patched_candidate.json");

    public static final String LIST_CANDIDATE_JSON = FileHelper.getContent("/candidate/list_candidate.json");

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

    public  static final  Skill SKILL_BY_CODE =
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
                    .office(OFFICE)
                    .role("Team Leader")
                    .name("Teach Leader at INDITEX")
                    .client(CLIENT_BY_CODE)
                    .closingDate(date)
                    .mode(EnumMode.REMOTE)
                    .openingDate(date)
                    .priority("HIGH")
                    .project(PROJECT_BY_CODE)
                    .skills(List.of(OPPORTUNITY_SKILL))
                    .build();

    public static final SkillsCandidate SKILLS_CANDIDATE =
            SkillsCandidate.builder()
                    .code("sc1120")
                    .experience(2)
                    .level(EnumLevel.LOW)
                    .build();

    public static final SkillsCandidate UPDATED_SKILLS_CANDIDATE =
            SkillsCandidate.builder()
                    .code("sc1120")
                    .experience(3)
                    .level(EnumLevel.HIGH)
                    .build();

    public static final People PEOPLE_BY_CODE =
            People.builder()
                    .code("pc1120")
                    .name("people")
                    .surname("surname")
                    .employeeId("employeeId")
                    .title(EnumTitle.SENIOR)
                    .birthDate(date)
                    .build();

    public static final Candidate CANDIDATE_BY_CODE =
            Candidate.builder()
                    .code("c1120")
                    .candidate(PEOPLE_BY_CODE)
                    .opportunity(OPPORTUNITY_BY_CODE)
                    .skills(List.of(SKILLS_CANDIDATE))
                    .build();

    public static final Candidate CANDIDATE2_BY_CODE =
            Candidate.builder()
                    .code("c1121")
                    .candidate(PEOPLE_BY_CODE)
                    .opportunity(OPPORTUNITY_BY_CODE)
                    .skills(List.of(SKILLS_CANDIDATE))
                    .build();

    public static final Candidate UPDATED_CANDIDATE_BY_CODE =
            Candidate.builder()
                    .code("c1120")
                    .candidate(PEOPLE_BY_CODE)
                    .opportunity(OPPORTUNITY_BY_CODE)
                    .skills(List.of(UPDATED_SKILLS_CANDIDATE))
                    .build();

    public static final List<Candidate> CANDIDATE_LIST = new ArrayList<Candidate>(){{
        add(CANDIDATE_BY_CODE);
        add(CANDIDATE2_BY_CODE);
    }};
}
