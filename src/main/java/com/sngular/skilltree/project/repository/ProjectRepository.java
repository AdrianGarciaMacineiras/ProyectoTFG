package com.sngular.skilltree.project.repository;

import com.sngular.skilltree.project.model.Project;
import java.util.List;

public interface ProjectRepository {
    List<Project> findAll();

    Project save(Project project);

    Project findByCode(String projectcode);

    boolean deleteByCode(String projectcode);
}
