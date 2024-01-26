package com.tfg.skilltree.infraestructura;

import java.util.List;

import com.tfg.skilltree.model.Project;
import com.tfg.skilltree.model.views.ProjectNamesView;

public interface ProjectRepository {

    List<Project> findAll();

    List<ProjectNamesView> findAllNames();

    Project save(Project project);

    Project findByCode(String projectCode);

    boolean deleteByCode(String projectCode);

    List<Project> findByDeletedIsFalse();

    Project findProject(String projectCode);
}
