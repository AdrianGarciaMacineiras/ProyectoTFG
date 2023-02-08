package com.sngular.skilltree.team.service;

import com.sngular.skilltree.application.TeamService;
import com.sngular.skilltree.application.TeamServiceImpl;
import com.sngular.skilltree.contract.mapper.TeamMapper;
import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.model.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.sngular.skilltree.team.service.TeamFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    private TeamService teamService;

    private TeamMapper mapper = Mappers.getMapper(TeamMapper.class);

    @BeforeEach
    void setUp(){teamService = new TeamServiceImpl(teamRepository);
    }

    @Test
    @DisplayName("Testing save team")
    void testSave(){
        when(teamRepository.save(TEAM_BY_CODE)).thenReturn(TEAM_BY_CODE);
        Team result = teamService.create(TEAM_BY_CODE);
        assertThat(result).isEqualTo(TEAM_BY_CODE);
    }

    @Test
    @DisplayName("Testing getAll the teams")
    void testGetAll(){
        when(teamRepository.findAll()).thenReturn(TEAM_LIST);
        List<Team> result = teamService.getAll();
        assertThat(result).containsExactly(TEAM_BY_CODE, TEAM2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a team")
    void testFindByCode(){
        when(teamRepository.findByCode(anyString())).thenReturn(TEAM_BY_CODE);
        Team result = teamService.findByCode("t1120");
        assertThat(result).isEqualTo(TEAM_BY_CODE);
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode(){
        when(teamRepository.deleteByCode(anyString())).thenReturn(true);
        lenient().when(teamRepository.findByCode(anyString())).thenReturn(TEAM_BY_CODE);
        boolean result = teamService.deleteByCode("t1120");
        assertThat(result).isTrue();
    }
}
