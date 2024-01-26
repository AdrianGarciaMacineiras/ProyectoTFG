package com.tfg.skilltree.application;

import static com.tfg.skilltree.application.TeamFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;

import com.tfg.skilltree.application.implement.TeamServiceImpl;
import com.tfg.skilltree.common.exceptions.EntityFoundException;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.infraestructura.TeamRepository;
import com.tfg.skilltree.model.Member;
import com.tfg.skilltree.model.Team;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    private TeamService teamService;

    @BeforeEach
    void setUp() {
        teamService = new TeamServiceImpl(teamRepository);
    }

    @Test
    @DisplayName("Testing save team")
    void testSave(){
        when(teamRepository.save(TEAM_BY_CODE)).thenReturn(TEAM_BY_CODE);
        Team result = teamService.create(TEAM_BY_CODE);
        assertThat(result).isEqualTo(TEAM_BY_CODE);
    }

    @Test
    @DisplayName("Test save team exception")
    void testSaveException(){
        when(teamRepository.findByCode(anyString())).thenReturn(TEAM_BY_CODE);
        Assertions.assertThrows(EntityFoundException.class, () ->
                teamService.create(TEAM_BY_CODE)
        );
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
    @DisplayName("Testing find by code exception null")
    void testFindByCodeExceptionNull(){
        when(teamRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                teamService.findByCode("t1120")
        );
    }

    @Test
    @DisplayName("Testing find by code exception deleted")
    void testFindByCodeExceptionDeleted(){
        when(teamRepository.findByCode(anyString())).thenReturn(TEAM_BY_CODE_DELETED_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                teamService.findByCode("t1120")
        );
    }

    @Test
    @DisplayName("Testing get all members of a team")
    void testGetMembers(){
        when(teamRepository.getMembers(anyString())).thenReturn(List.of(MEMBER1));
        when(teamRepository.findByCode(anyString())).thenReturn(TEAM_BY_CODE);
        List<Member> result = teamService.getMembers("t1120");
        assertThat(result).containsExactly(MEMBER1);
    }

    @Test
    @DisplayName("Testing get members exception null")
    void testGetMembersExceptionNull(){
        when(teamRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                teamService.getMembers("t1120")
        );
    }

    @Test
    @DisplayName("Testing get members exception deleted")
    void testGetMembersExceptionDeleted(){
        when(teamRepository.findByCode(anyString())).thenReturn(TEAM_BY_CODE_DELETED_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                teamService.getMembers("t1120")
        );
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode(){
        when(teamRepository.deleteByCode(anyString())).thenReturn(true);
        lenient().when(teamRepository.findByCode(anyString())).thenReturn(TEAM_BY_CODE);
        boolean result = teamService.deleteByCode("t1120");
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Testing delete exception null")
    void testDeleteByCodeExceptionNull(){
        when(teamRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                teamService.deleteByCode("t1120")
        );
    }

    @Test
    @DisplayName("Testing delete exception deleted")
    void testDeleteByCodeExceptionDeleted(){
        when(teamRepository.findByCode(anyString())).thenReturn(TEAM_BY_CODE_DELETED_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                teamService.deleteByCode("t1120")
        );
    }
}
