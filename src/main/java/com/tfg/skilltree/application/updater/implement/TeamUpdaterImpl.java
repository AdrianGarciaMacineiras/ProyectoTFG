package com.tfg.skilltree.application.updater.implement;

import java.util.Objects;

import com.tfg.skilltree.application.updater.TeamUpdater;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.contract.mapper.TeamMapper;
import com.tfg.skilltree.infraestructura.TeamRepository;
import com.tfg.skilltree.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamUpdaterImpl implements TeamUpdater {

    private final TeamRepository teamRepository;

    private final TeamMapper mapper;

    @Override
    public Team update(String teamcode, Team newTeam) {
        validate(teamcode);
        return teamRepository.save(newTeam);
    }

    @Override
    public Team patch(String teamcode, Team patchedTeam) {
        validate(teamcode);
        var oldTeam = teamRepository.findByCode(teamcode);
        var team = mapper.patch(patchedTeam, oldTeam);
        return teamRepository.save(team);
    }

    private void validate(String code) {
        var oldTeam = teamRepository.findByCode(code);
        if (Objects.isNull(oldTeam) || oldTeam.deleted()) {
            throw new EntityNotFoundException("Team", code);
        }
    }
}
