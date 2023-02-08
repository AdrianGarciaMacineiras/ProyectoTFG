package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Team;

public interface TeamUpdater {

    Team update(final String teamcode, final Team newTeam);

    Team patch(final String teamcode, final Team patchedTeam);
}
