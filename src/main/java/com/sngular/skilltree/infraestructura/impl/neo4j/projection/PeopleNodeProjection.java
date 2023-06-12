package com.sngular.skilltree.infraestructura.impl.neo4j.projection;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.EnumTitle;

import java.time.LocalDate;

public interface PeopleNodeProjection {

    String getCode();

    String getName();

    String getEmployeeId();

    String getSurname();

    LocalDate getBirthDate();

    EnumTitle getTitle();

    boolean getDeleted();

    boolean getAssignable();
}
