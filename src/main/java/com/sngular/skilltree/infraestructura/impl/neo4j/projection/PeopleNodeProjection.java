package com.sngular.skilltree.infraestructura.impl.neo4j.projection;

import java.time.LocalDate;

public interface PeopleNodeProjection {

  String getCode();

  String getName();

  String getEmployeeId();

  String getSurname();

  LocalDate getBirthDate();

  String getTitle();

  boolean getDeleted();

  boolean getAssignable();
}
