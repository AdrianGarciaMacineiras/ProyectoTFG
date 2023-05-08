package com.sngular.skilltree.fixtures;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.sngular.skilltree.model.EnumTitle;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.testutil.FileHelper;

public final class PersonFixtures {

  public static LocalDate date = LocalDate.parse("20-01-2023", DateTimeFormatter.ofPattern("dd-MM-yyyy"));

  public static final String UPDATED_PERSON_BY_CODE_JSON = FileHelper.getContent("/person/updated_person_by_code.json");

  public static final String PERSON_BY_CODE_JSON = FileHelper.getContent("/person/person_by_code.json");

  public static final String PATCH_PERSON_BY_CODE_JSON = FileHelper.getContent("/person/patched_person.json");

  public static final String LIST_PERSON_JSON = FileHelper.getContent("/person/list_person.json");

  public static final People PEOPLE_BY_CODE =
        People.builder()
                .code(1L)
                .name("people2")
                .surname("LaPel")
                .employeeId("900003940059")
                .title(EnumTitle.DEVELOPER)
                .birthDate(date)
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
