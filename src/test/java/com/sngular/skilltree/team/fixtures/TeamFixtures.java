package com.sngular.skilltree.team.fixtures;

import com.sngular.skilltree.model.*;
import com.sngular.skilltree.testutil.FileHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TeamFixtures {

    public static final Date date = new GregorianCalendar(2023, Calendar.JANUARY, 20).getTime();

    public static final String TEAM_BY_CODE_JSON = FileHelper.getContent("/team/team_by_code.json");

    public static final String UPDATED_TEAM_BY_CODE_JSON = FileHelper.getContent("/team/updated_team_by_code.json");

    public static final String PATCHED_TEAM_BY_CODE_JSON = FileHelper.getContent("/team/patched_team_by_code.json");

    public static final String LIST_TEAM_JSON = FileHelper.getContent("/team/list_team.json");

    public static final People PEOPLE_BY_CODE =
            People.builder()
                    .code("pc1120")
                    .name("people2")
                    .surname("LaPel")
                    .employeeId("900003940059")
                    .title(EnumTitle.DEVELOPER)
                    .birthDate(date)
                    .build();

    public final static Member MEMBER1 =
            Member.builder()
                    .position(EnumPosition.DIRECTOR)
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
