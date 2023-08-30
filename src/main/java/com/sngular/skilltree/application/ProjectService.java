package com.sngular.skilltree.application;

import java.util.List;

import com.sngular.skilltree.model.Project;

public interface ProjectService {

    List<Project> getAll();

    Project create (final Project toProject);

    Project findByCode(final String projectCode);

    boolean deleteByCode(final String projectCode);

    Project findProject(final String projectCode);

}
