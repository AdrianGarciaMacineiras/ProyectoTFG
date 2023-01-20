package com.sngular.skilltree.person.fixtures;

import com.sngular.skilltree.model.EnumTitle;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.testutil.FileHelper;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class PersonFixtures {

  public static final Date date = new GregorianCalendar(2023, Calendar.JANUARY, 20).getTime();

  public static final String PERSON_BY_CODE_JSON = FileHelper.getContent("/person/person_by_code.json");

  public static final String UPDATED_PERSON_BY_CODE_JSON = FileHelper.getContent("/person/updated_person_by_code.json");

  public static final String PERSONDTO_BY_CODE_JSON = FileHelper.getContent("/person/persondto.json");


  public static final People PEOPLE_BY_CODE =
        People.builder()
                .code("pc1120")
                .name("people")
                .surname("surname")
                .title(EnumTitle.SENIOR)
                .birthDate(date)
                .build();

  public static final People UPDATED_PEOPLE_BY_CODE =
          People.builder()
                  .code("pc1120")
                  .name("people2")
                  .surname("surname2")
                  .title(EnumTitle.DEVELOPER)
                  .birthDate(date)
                  .build();
}
