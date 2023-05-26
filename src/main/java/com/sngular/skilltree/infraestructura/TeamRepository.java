package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Team;
import java.util.List;

public interface TeamRepository {

    List<Team> findAll();

    Team save(Team project);

    Team findByCode(String teamcode);

    List<People> getMembers(String teamcode);

    boolean deleteByCode(String teamcode);

    List<Team> findByDeletedIsFalse();
}
