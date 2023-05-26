package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Team;
import com.sngular.skilltree.model.People;


import java.util.List;

public interface TeamService {

    List<Team> getAll();

    Team create(final Team team);

    Team findByCode(final String teamcode);

    List<People> getMembers(final String teamcode);

    boolean deleteByCode(final String teamcode);

}
