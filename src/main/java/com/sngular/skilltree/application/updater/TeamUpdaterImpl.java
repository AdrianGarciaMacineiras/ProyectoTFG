package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.contract.mapper.TeamMapper;
import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamUpdaterImpl implements TeamUpdater{

    private final TeamRepository teamRepository;

    private final TeamMapper mapper;

    @Override
    public Team update(String teamcode, Team newTeam) {
        var oldTeam = teamRepository.findByCode(teamcode);
        mapper.update(oldTeam, newTeam);
        return teamRepository.save(newTeam);
    }

    @Override
    public Team patch(String teamcode, Team patchedTeam) {
        var oldTeam = teamRepository.findByCode(teamcode);
        var team = mapper.update(patchedTeam, oldTeam);
        return teamRepository.save(team);
    }
}
