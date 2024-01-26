package com.tfg.skilltree.infraestructura;

import java.util.List;

import com.tfg.skilltree.model.Member;
import com.tfg.skilltree.model.Team;

public interface TeamRepository {

    List<Team> findAll();

    Team save(Team project);

    Team findByCode(String teamcode);

    List<Member> getMembers(String teamcode);

    boolean deleteByCode(String teamcode);

    List<Team> findByDeletedIsFalse();
}
