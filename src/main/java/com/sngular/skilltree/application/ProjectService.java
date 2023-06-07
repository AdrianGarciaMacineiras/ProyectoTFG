package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getAll();

    Project create (final Project toProject);

    Project findByCode(final String projectCode);

    boolean deleteByCode(final String projectCode);

    Project findProject(final String projectCode);

}
