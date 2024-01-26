package com.tfg.skilltree.application.updater;

import com.tfg.skilltree.model.Team;

public interface TeamUpdater {

    Team update(final String teamcode, final Team newTeam);

    Team patch(final String teamcode, final Team patchedTeam);
}
