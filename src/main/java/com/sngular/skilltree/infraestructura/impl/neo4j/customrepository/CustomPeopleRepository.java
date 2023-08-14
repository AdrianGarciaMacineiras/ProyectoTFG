package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PeopleExtendedView;

import java.util.List;


public interface CustomPeopleRepository {

    PeopleExtendedView getAllPeopleExtended();
}
