package com.sngular.skilltree.application;

import com.sngular.skilltree.CommonFixtures;
import com.sngular.skilltree.model.*;

import java.util.ArrayList;
import java.util.List;

public class CandidateFixtures extends CommonFixtures {

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
                    .country("Spain")
                    .industry("RETAIL")
                    .name("Inditex")
                    .offices(List.of(OFFICE))
                    .build();

    public  static final Skill SKILL_BY_CODE =
            Skill.builder()
                    .code("s1120")
                    .name("Spring")
                    .build();

    public static final PositionSkill POSITION_SKILL =
            PositionSkill.builder()
                    .id("positionSkill1")
                    .levelReq(EnumLevelReq.MANDATORY)
                    .minExp(10)
                    .levelReq(EnumLevelReq.MANDATORY)
                    .skill(SKILL_BY_CODE)
                    .build();


    public static final ProjectRoles ROLES =
            ProjectRoles.builder()
                    .rol("senior")
                    .number(2)
                    .build();

    public  static final Project PROJECT_BY_CODE =
            Project.builder()
                    .code("1")
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

    public static final Position POSITION_BY_CODE =
            Position.builder()
                    .code("itxtl1")
                    .office(OFFICE)
                    .role("Team Leader")
                    .name("Teach Leader at INDITEX")
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
                    .code("1")
                    .name("people")
                    .surname("surname")
                    .employeeId("employeeId")
                    .title("SD3")
                    .birthDate(date)
                    .build();

    public static final Candidate CANDIDATE_BY_CODE =
            Candidate.builder()
                    .code("c1120")
                    .candidate(PEOPLE_BY_CODE)
                    .position(POSITION_BY_CODE)
                    .build();

    public static final Candidate CANDIDATE2_BY_CODE =
            Candidate.builder()
                    .code("c1122")
                    .candidate(PEOPLE_BY_CODE)
                    .position(POSITION_BY_CODE)
                    .build();

    public static final List<Candidate> CANDIDATE_LIST = new ArrayList<>(){{
        add(CANDIDATE_BY_CODE);
        add(CANDIDATE2_BY_CODE);
    }};
}
