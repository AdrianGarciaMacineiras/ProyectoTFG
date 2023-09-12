package com.sngular.skilltree.application;

import java.util.List;

import com.sngular.skilltree.model.Project;
import com.sngular.skilltree.model.views.ProjectNamesView;

public interface ProjectService {

    List<Project> getAll();

    List<ProjectNamesView> getAllNames();

    Project create (final Project toProject);

    Project findByCode(final String projectCode);

    boolean deleteByCode(final String projectCode);

    Project findProject(final String projectCode);

}
