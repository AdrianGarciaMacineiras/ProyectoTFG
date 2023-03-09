package com.sngular.skilltree.application;

import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService{

    private final TeamRepository teamRepository;

    @Override
    public List<Team> getAll() {
        return teamRepository.findByDeletedIsFalse();
    }

    @Override
    public Team create(Team team) {
        validateExist(team.code());
        return teamRepository.save(team);
    }

    @Override
    public Team findByCode(String teamcode) {
        var team = teamRepository.findByCode(teamcode);
        if (Objects.isNull(team) || team.deleted())
            throw new EntityNotFoundException("Team", teamcode);
        return team;
    }

    @Override
    public boolean deleteByCode(String teamcode) {
        validateDoesNotExist(teamcode);
        return teamRepository.deleteByCode(teamcode);
    }

    private void validateExist(String code) {
        var oldTeam = teamRepository.findByCode(code);
        if (!Objects.isNull(oldTeam) && !oldTeam.deleted()) {
            throw new EntityFoundException("Team", code);
        }
    }

    private void validateDoesNotExist(String code) {
        var oldTeam = teamRepository.findByCode(code);
        if (Objects.isNull(oldTeam) || oldTeam.deleted()) {
            throw new EntityNotFoundException("Team", code);
        }
    }
}
