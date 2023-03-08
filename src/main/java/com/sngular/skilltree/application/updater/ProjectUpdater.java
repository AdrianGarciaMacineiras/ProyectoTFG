package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Project;

public interface ProjectUpdater {

    Project update(final Integer projectcode, final Project newProject);

    Project patch(final Integer projectcode, final Project patchedProject);
}
