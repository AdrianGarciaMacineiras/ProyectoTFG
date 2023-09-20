package com.sngular.skilltree.infraestructura;

import java.util.List;

import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.PeopleSkill;
import com.sngular.skilltree.model.views.PeopleNamesView;
import com.sngular.skilltree.model.views.PeopleView;

public interface PeopleRepository {

    List<People> findAll();

    List<PeopleView> findAllResumed();

    List<PeopleNamesView> findAllNames();

    People save(People people);

    People findByCode(String personCode);

    People findPeopleByCode(String personCode);

    boolean deleteByCode(String personCode);

    List<People> findByDeletedIsFalse();

  List<People> getPeopleBySkills(List<PeopleSkill> skills);

    List<People> getOtherPeopleStrategicSkills(String teamCode);
}
