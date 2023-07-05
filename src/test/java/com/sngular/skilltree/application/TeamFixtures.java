package com.sngular.skilltree.application;

import com.sngular.skilltree.CommonFixtures;
import com.sngular.skilltree.model.EnumCharge;
import com.sngular.skilltree.model.Member;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Team;

import java.util.List;

public class TeamFixtures extends CommonFixtures {

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

    public final static Team TEAM_BY_CODE_DELETED_TRUE =
            Team.builder()
                    .code("t1120")
                    .tags(List.of("Project"))
                    .name("TeamName")
                    .description("Team description")
                    .members(List.of(MEMBER1))
                    .deleted(true)
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
