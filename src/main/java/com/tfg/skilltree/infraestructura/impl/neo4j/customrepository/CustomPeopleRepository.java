package com.tfg.skilltree.infraestructura.impl.neo4j.customrepository;

import java.util.List;

import com.tfg.skilltree.infraestructura.impl.neo4j.querymodel.PeopleExtendedView;
import com.tfg.skilltree.infraestructura.impl.neo4j.querymodel.PeopleNamesView;


public interface CustomPeopleRepository {

    List<PeopleExtendedView> getAllPeopleExtended();

    List<PeopleNamesView> getAllPeopleNames();
}
