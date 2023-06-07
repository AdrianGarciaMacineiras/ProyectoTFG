package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.People;
import java.util.List;

public interface PeopleRepository {

    List<People> findAll();

    People save(People people);

    People findByCode(String personCode);

    People findPeopleByCode(String personCode);

    boolean deleteByCode(String personCode);

    List<People> findByDeletedIsFalse();

    List<People> getPeopleSkills(List<String> skills);

    List<People> getOtherPeopleStrategicSkills(String teamCode);
}
