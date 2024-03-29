package com.sngular.skilltree.fixtures;

import com.sngular.skilltree.CommonFixtures;
import com.sngular.skilltree.model.*;
import com.sngular.skilltree.testutil.FileHelper;

import java.util.List;

public class PositionFixtures extends CommonFixtures {

    public static final String POSITION_BY_CODE_JSON = FileHelper.getContent("/position/position_by_code.json");

    public static final String UPDATED_POSITION_BY_CODE_JSON = FileHelper.getContent("/position/updated_position_by_code.json");

    public static final String PATCH_POSITION_BY_CODE_JSON = FileHelper.getContent("/position/patched_position.json");

    public static final String LIST_POSITION_JSON = FileHelper.getContent("/position/list_position.json");

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

    public static final People PEOPLE_BY_CODE =
            People.builder()
                    .code("1")
                    .name("people")
                    .surname("surname")
                    .employeeId("employeeId")
                    .title("SD3")
                    .birthDate(date)
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
                    .name("Tech Leader at INDITEX")
                    .closingDate(date)
                    .mode(EnumMode.REMOTE)
                    .openingDate(date)
                    .priority("HIGH")
                    .project(PROJECT_BY_CODE)
                    .managedBy(PEOPLE_BY_CODE)
                    .skills(List.of(OPPORTUNITY_SKILL))
                    .build();

    public static final Position UPDATED_POSITION_BY_CODE =
            Position.builder()
                    .code("itxtl1")
                    .office(OFFICE)
                    .role("Leader")
                    .name("Leader at INDITEX")
                    .closingDate(date)
                    .mode(EnumMode.REMOTE)
                    .openingDate(date)
                    .priority("MEDIUM")
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
                    .closingDate(date)
                    .mode(EnumMode.REMOTE)
                    .openingDate(date)
                    .priority("HIGH")
                    .project(PROJECT_BY_CODE)
                    .managedBy(PEOPLE_BY_CODE)
                    .skills(List.of(OPPORTUNITY_SKILL))
                    .build();

    public static final List<Position> POSITION_LIST = List.of(POSITION_BY_CODE, POSITION_2_BY_CODE);
}