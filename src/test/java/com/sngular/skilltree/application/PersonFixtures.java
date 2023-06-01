package com.sngular.skilltree.application;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.sngular.skilltree.model.*;

public final class PersonFixtures {

  public static LocalDate date = LocalDate.parse("20-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

  public static final Assignment ASSIGNMENT =
          Assignment.builder()
                  .assignDate(date)
                  .role("TechLeader")
                  .id("a1120")
                  .dedication(100)
                  .build();

  public static final Assignments ASSIGNMENTS =
          Assignments.builder()
                  .name("itxtl1")
                  .assignments(List.of(ASSIGNMENT))
                  .build();

  public static final People PEOPLE_BY_CODE =
    People.builder()
            .code(1L)
            .name("people")
            .surname("surname")
            .employeeId("employeeId")
            .title(EnumTitle.SENIOR)
            .birthDate(date)
            .assignable(true)
            .assigns(List.of(ASSIGNMENTS))
            .build();

  public static final People PEOPLE2_BY_CODE =
          People.builder()
                  .code(2L)
                  .name("people3")
                  .surname("surname3")
                  .employeeId("employeeId")
                  .title(EnumTitle.SENIOR)
                  .birthDate(date)
                  .build();

  public static final People UPDATED_PEOPLE_BY_CODE =
          People.builder()
                  .code(1L)
                  .name("people2")
                  .surname("surname2")
                  .employeeId("employeeId")
                  .title(EnumTitle.DEVELOPER)
                  .birthDate(date)
                  .build();

  public static final List<People> PEOPLE_LIST = new ArrayList<People>(){{
    add(PEOPLE_BY_CODE);
    add(PEOPLE2_BY_CODE);
  }};
}
