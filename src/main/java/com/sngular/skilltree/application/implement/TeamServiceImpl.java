package com.sngular.skilltree.application.implement;

import java.util.List;
import java.util.Objects;
import com.sngular.skilltree.application.TeamService;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.model.Member;
import com.sngular.skilltree.model.Team;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;

    @Override
    @Cacheable(cacheNames = "teams")
    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    @Override
    @CachePut(cacheNames = "teams", key = "#team.shortName")
    @CacheEvict(cacheNames = "teams")
    public Team create(final Team team) {
        validateExist(team.code());
        return teamRepository.save(team);
    }

    @Override
    public Team findByCode(final String teamcode) {
        var team = teamRepository.findByCode(teamcode);
        if (Objects.isNull(team) || team.deleted())
            throw new EntityNotFoundException("Team", teamcode);
        return team;
    }

    @Override
    public List<Member> getMembers(final String teamcode) {
        validateDoesNotExist(teamcode);
        return teamRepository.getMembers(teamcode);
    }

    @Override
    @CacheEvict(cacheNames = "teams")
    public boolean deleteByCode(final String teamCode) {
        validateDoesNotExist(teamCode);
        return teamRepository.deleteByCode(teamCode);
    }

    private void validateExist(final String code) {
        var oldTeam = teamRepository.findByCode(code);
        if (!Objects.isNull(oldTeam) && !oldTeam.deleted()) {
            throw new EntityFoundException("Team", code);
        }
    }

    private void validateDoesNotExist(final String code) {
        var oldTeam = teamRepository.findByCode(code);
        if (Objects.isNull(oldTeam) || oldTeam.deleted()) {
            throw new EntityNotFoundException("Team", code);
        }
    }
}
