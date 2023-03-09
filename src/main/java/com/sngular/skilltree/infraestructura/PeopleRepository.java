package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.People;
import java.util.List;

public interface PeopleRepository {

    List<People> findAll();

    People save(People people);

    People findByCode(Long personcode);

    boolean deleteByCode(Long personcode);

    List<People> findByDeletedIsFalse();
}
