package com.sngular.skilltree.application;

import java.util.List;

import com.sngular.skilltree.model.Member;
import com.sngular.skilltree.model.Team;

public interface TeamService {

    List<Team> getAll();

    Team create(final Team team);

    Team findByCode(final String teamcode);

    List<Member> getMembers(final String teamcode);

    boolean deleteByCode(final String teamcode);

}
