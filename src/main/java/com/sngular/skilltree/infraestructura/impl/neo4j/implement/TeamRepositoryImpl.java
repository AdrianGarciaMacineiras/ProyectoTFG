package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.TeamCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.TeamNodeMapper;
import com.sngular.skilltree.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

    private final TeamCrudRepository crud;

    private final TeamNodeMapper mapper;

    private final PeopleCrudRepository peopleCrudRepository;

    @Override
    public List<Team> findAll() { return mapper.map(crud.findByDeletedIsFalse()); }

    @Override
    public Team save(Team team) {
        var teamNode = mapper.toNode(team);
        for (var member : teamNode.getMembers()){
            var peopleNode = peopleCrudRepository.findByCode(member.peopleNode().getCode());
            if (Objects.isNull(peopleNode) || peopleNode.isDeleted()) {
                throw new EntityNotFoundException("People", peopleNode.getCode());
            }
        }
        return mapper.fromNode(crud.save(teamNode));
    }

    @Override
    public Team findByCode(String teamcode) { return mapper.fromNode(crud.findByCode(teamcode)); }

    @Override
    public boolean deleteByCode(String teamcode) {
        var node = crud.findByCode(teamcode);
        node.setDeleted(true);
        crud.save(node);
        return true;
    }

    @Override
    public List<Team> findByDeletedIsFalse() {
        return mapper.map(crud.findByDeletedIsFalse());
    }
}
