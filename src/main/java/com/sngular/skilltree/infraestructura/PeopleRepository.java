package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.views.PeopleView;

import java.util.List;

public interface PeopleRepository {

    List<People> findAll();

    List<PeopleView> findAllResumed();

    People save(People people);

    People findByCode(String personCode);

    People findPeopleByCode(String personCode);

    boolean deleteByCode(String personCode);

    List<People> findByDeletedIsFalse();

    List<People> getPeopleSkills(List<String> skills);

    List<People> getOtherPeopleStrategicSkills(String teamCode);
}
