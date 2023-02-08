package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Project;

import java.util.List;

public interface ProjectService {

    List<Project> getAll();

    Project create (final Project toProject);

    Project findByCode(final String projectcode);

    boolean deleteByCode(final String projectcode);

}
