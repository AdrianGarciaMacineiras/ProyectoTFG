package com.sngular.skilltree.person.fixtures;

import com.sngular.skilltree.person.model.Person;
import com.sngular.skilltree.testutil.FileHelper;

public final class PersonFixtures {

  public static final String PESON_BY_CODE_JSON = FileHelper.getContent("/person/person_by_code.json");

  public static final Person PERSON_BY_CODE =
        Person.builder().code("pc1120").name("person").build();
}
