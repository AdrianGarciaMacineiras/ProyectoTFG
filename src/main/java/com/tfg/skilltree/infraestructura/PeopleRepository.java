package com.tfg.skilltree.infraestructura;

import java.util.List;

import com.tfg.skilltree.model.People;
import com.tfg.skilltree.model.PeopleSkill;
import com.tfg.skilltree.model.views.PeopleNamesView;
import com.tfg.skilltree.model.views.PeopleView;

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
