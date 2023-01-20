package com.sngular.skilltree.person.service;

import com.sngular.skilltree.model.People;
import com.sngular.skilltree.testutil.FileHelper;

public final class PersonFixtures {

  public static final String PERSON_BY_CODE_JSON = FileHelper.getContent("/people/person_by_code.json");

  public static final People PEOPLE_BY_CODE =
        People.builder().code("pc1120").name("people").build();
}
