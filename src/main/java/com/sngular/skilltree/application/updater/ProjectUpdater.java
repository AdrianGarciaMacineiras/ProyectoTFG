package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Project;

public interface ProjectUpdater {

    Project update(final Long projectcode, final Project newProject);

    Project patch(final Long projectcode, final Project patchedProject);
}
