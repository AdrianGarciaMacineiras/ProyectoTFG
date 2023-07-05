package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.infraestructura.impl.neo4j.projection.TeamProjection;
import com.sngular.skilltree.model.Member;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Team;
import java.util.List;

public interface TeamRepository {

    List<Team> findAll();

    Team save(Team project);

    Team findByCode(String teamcode);

    List<Member> getMembers(String teamcode);

    boolean deleteByCode(String teamcode);

    List<Team> findByDeletedIsFalse();
}
