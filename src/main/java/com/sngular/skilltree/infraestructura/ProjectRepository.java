package com.sngular.skilltree.infraestructura;

import java.util.List;

import com.sngular.skilltree.model.Project;

public interface ProjectRepository {

    List<Project> findAll();

    Project save(Project project);

    Project findByCode(String projectCode);

    boolean deleteByCode(String projectCode);

    List<Project> findByDeletedIsFalse();

    Project findProject(String projectCode);
}
