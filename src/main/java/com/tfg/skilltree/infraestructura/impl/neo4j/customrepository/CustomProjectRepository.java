package com.tfg.skilltree.infraestructura.impl.neo4j.customrepository;

import com.tfg.skilltree.infraestructura.impl.neo4j.querymodel.ProjectNamesView;

import java.util.List;

public interface CustomProjectRepository {

    List<ProjectNamesView> getAllProjectNames();
}
