package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Project;
import java.util.List;

public interface ProjectRepository {

    List<Project> findAll();

    Project save(Project project);

    Project findByCode(Long projectcode);

    boolean deleteByCode(Long projectcode);

    List<Project> findByDeletedIsFalse();
}
