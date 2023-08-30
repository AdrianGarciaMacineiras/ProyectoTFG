package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import java.util.List;

import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PeopleExtendedView;


public interface CustomPeopleRepository {

    List<PeopleExtendedView> getAllPeopleExtended();
}
