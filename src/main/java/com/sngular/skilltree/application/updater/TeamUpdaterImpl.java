package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.mapper.TeamMapper;
import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.TeamCrudRepository;
import com.sngular.skilltree.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamUpdaterImpl implements TeamUpdater{

    private final TeamRepository teamRepository;

    private final TeamMapper mapper;

    private final TeamCrudRepository crud;

    @Override
    public Team update(String teamcode, Team newTeam) {
        validate(teamcode);
        crud.detachDelete(teamcode);
        return teamRepository.save(newTeam);
    }

    @Override
    public Team patch(String teamcode, Team patchedTeam) {
        validate(teamcode);
        var oldTeam = teamRepository.findByCode(teamcode);
        var team = mapper.update(patchedTeam, oldTeam);
        crud.detachDelete(teamcode);
        return teamRepository.save(team);
    }

    private void validate(String code) {
        var oldTeam = teamRepository.findByCode(code);
        if (Objects.isNull(oldTeam) || oldTeam.deleted()) {
            throw new EntityNotFoundException("Team", code);
        }
    }
}
