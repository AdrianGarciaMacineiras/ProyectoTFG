package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.TeamNodeMapper;
import com.sngular.skilltree.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

    private final TeamCrudRepository crud;

    private final TeamNodeMapper mapper;

    @Override
    public List<Team> findAll() { return mapper.map(crud.findAll()); }

    @Override
    public Team save(Team team) { return mapper.fromNode(crud.save(mapper.toNode(team))); }

    @Override
    public Team findByCode(String teamcode) { return mapper.fromNode(crud.findByCode(teamcode)); }

    @Override
    public boolean deleteByCode(String teamcode) {
        var node = crud.findByCode(teamcode);
        node.setDeleted(true);
        crud.save(node);
        //crud.delete(node);
        return true;
    }

    @Override
    public List<Team> findByDeletedIsFalse() {
        return mapper.map(crud.findByDeletedIsFalse());
    }
}
