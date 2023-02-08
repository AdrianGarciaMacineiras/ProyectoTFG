package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Project;

public interface ProjectUpdater {

    Project update(final String projectcode, final Project newProject);

    Project patch(final String projectcode, final Project patchedProject);
}
