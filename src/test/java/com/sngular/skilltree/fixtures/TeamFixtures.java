package com.sngular.skilltree.fixtures;

import com.sngular.skilltree.CommonFixtures;
import com.sngular.skilltree.model.EnumCharge;
import com.sngular.skilltree.model.Member;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Team;
import com.sngular.skilltree.testutil.FileHelper;

import java.util.List;

public class TeamFixtures extends CommonFixtures {

    public static final String TEAM_BY_CODE_JSON = FileHelper.getContent("/team/team_by_code.json");

    public static final String UPDATED_TEAM_BY_CODE_JSON = FileHelper.getContent("/team/updated_team_by_code.json");

    public static final String PATCHED_TEAM_BY_CODE_JSON = FileHelper.getContent("/team/patched_team_by_code.json");

    public static final String LIST_TEAM_JSON = FileHelper.getContent("/team/list_team.json");

    public static final People PEOPLE_BY_CODE =
            People.builder()
                    .code("1")
                    .name("people2")
                    .surname("LaPel")
                    .employeeId("900003940059")
                    .title("SD2")
                    .birthDate(date)
                    .build();

    public final static Member MEMBER1 =
            Member.builder()
                    .charge(EnumCharge.DIRECTOR.name())
                    .people(PEOPLE_BY_CODE)
                    .build();

    public final static Team TEAM_BY_CODE =
            Team.builder()
                    .code("t1120")
                    .tags(List.of("Project"))
                    .name("TeamName")
                    .description("Team description")
                    .members(List.of(MEMBER1))
                    .build();

    public final static Team TEAM2_BY_CODE =
            Team.builder()
                    .code("t1121")
                    .tags(List.of("Backend"))
                    .name("TeamName2")
                    .description("Team2 description")
                    .members(List.of(MEMBER1))
                    .build();

    public final static List<Team> TEAM_LIST = List.of(TEAM_BY_CODE, TEAM2_BY_CODE);

    public final static Team UPDATED_TEAM_BY_CODE =
            Team.builder()
                    .code("t1120")
                    .tags(List.of("Develop","Frontend"))
                    .name("TeamFrontend")
                    .description("Team description")
                    .members(List.of(MEMBER1))
                    .build();
}
