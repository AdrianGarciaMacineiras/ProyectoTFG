package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Project;

public interface ProjectUpdater {

    Project update(final String projectCode, final Project newProject);

    Project patch(final String projectCode, final Project patchedProject);
}
