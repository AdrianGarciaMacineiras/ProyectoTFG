package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Team;

import java.util.List;

public interface TeamService {

    List<Team> getAll();

    Team create(final Team team);

    Team findByCode(final String teamcode);

    boolean deleteByCode(final String teamcode);

    Team update(final String teamcode, final Team newTeam);

    Team patch(final String teamcode, final Team patchedTeam);
}