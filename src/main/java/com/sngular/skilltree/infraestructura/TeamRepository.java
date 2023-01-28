package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Team;
import java.util.List;

public interface TeamRepository {

    List<Team> findAll();

    Team save(Team project);

    Team findByCode(String teamcode);

    boolean deleteByCode(String teamcode);
}
