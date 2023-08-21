package com.sngular.skilltree.application;

import com.sngular.skilltree.CommonFixtures;
import com.sngular.skilltree.model.Assignment;
import com.sngular.skilltree.model.Assignments;
import com.sngular.skilltree.model.People;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class PersonFixtures extends CommonFixtures {

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
                  .code("1")
                  .name("people")
                  .surname("surname")
                  .employeeId("employeeId")
                  .title("SD3")
            //.birthDate(date)
            .assignable(true)
            .assigns(List.of(ASSIGNMENTS))
            .deleted(false)
            .candidacies(Collections.emptyList())
            .build();

  public static final People PEOPLE_BY_CODE_DELETE_TRUE =
          People.builder()
                  .code("1")
                  .name("people")
                  .surname("surname")
                  .employeeId("employeeId")
                  .title("SD3")
                  //.birthDate(date)
                  .assignable(true)
                  .assigns(List.of(ASSIGNMENTS))
                  .deleted(true)
                  .build();

  public static final People PEOPLE2_BY_CODE =
          People.builder()
                  .code("2")
                  .name("people3")
                  .surname("surname3")
                  .employeeId("employeeId")
                  .title("SD3")
                  //.birthDate(date)
                  .build();

  public static final People UPDATED_PEOPLE_BY_CODE =
          People.builder()
                  .code("1")
                  .name("people2")
                  .surname("surname2")
                  .employeeId("employeeId")
                  .title("SD2")
                  //.birthDate(date)
                  .build();

  public static final List<People> PEOPLE_LIST = new ArrayList<>() {{
    add(PEOPLE_BY_CODE);
    add(PEOPLE2_BY_CODE);
  }};


}
