package com.tfg.skilltree.application.updater;

import com.tfg.skilltree.model.Project;

public interface ProjectUpdater {

    Project update(final String projectCode, final Project newProject);

    Project patch(final String projectCode, final Project patchedProject);
}
