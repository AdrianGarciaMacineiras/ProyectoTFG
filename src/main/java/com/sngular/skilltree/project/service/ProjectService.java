package com.sngular.skilltree.project.service;

import com.sngular.skilltree.project.model.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getALl();

    Project create (final Project toProject);

    Project findByCode(final String projectcode);

    boolean deleteByCode(final String projectcode);

    Project update(final String projectcode, final Project toProject);

    Project patch(final String projectcode, final Project toProject);
}
