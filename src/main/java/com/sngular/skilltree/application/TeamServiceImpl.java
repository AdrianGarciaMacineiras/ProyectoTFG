package com.sngular.skilltree.application;

import com.sngular.skilltree.contract.mapper.TeamMapper;
import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;

    private final TeamMapper mapper;

    @Override
    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    @Override
    public Team create(Team team) {
        validate(team);
        return teamRepository.save(team);
    }

    @Override
    public Team findByCode(String teamcode) {
        return teamRepository.findByCode(teamcode);
    }

    @Override
    public boolean deleteByCode(String teamcode) {
        return teamRepository.deleteByCode(teamcode);
    }

    @Override
    public Team update(String teamcode, Team newTeam) {
        var oldTeam = teamRepository.findByCode(teamcode);
        mapper.update(oldTeam, newTeam);
        return teamRepository.save(oldTeam);
    }

    @Override
    public Team patch(String teamcode, Team patchedTeam) {
        var oldTeam = teamRepository.findByCode(teamcode);
        mapper.update(oldTeam, patchedTeam);
        return teamRepository.save(oldTeam);    }

    private void validate(Team team) {
    }
}
