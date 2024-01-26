package com.tfg.skilltree.contract;

import com.tfg.skilltree.application.updater.TeamUpdater;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.model.Team;
import com.tfg.skilltree.application.*;
import com.tfg.skilltree.contract.mapper.*;
import com.tfg.skilltree.fixtures.TeamFixtures;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamUpdater teamUpdater;

    @Autowired
    private TeamService teamService;

    @Test
    void getTeamByCode() throws Exception{
        when(teamService.findByCode(anyString())).thenReturn(TeamFixtures.TEAM_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/team/team1120")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(TeamFixtures.TEAM_BY_CODE_JSON));
    }

    @Test
    void shouldDeleteTeamBySuccess() throws Exception{
        when(teamService.deleteByCode(anyString())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/team/team1120")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldDeleteTeamFail() throws Exception{
        when(teamService.deleteByCode(anyString())).thenThrow(new EntityNotFoundException("Team", "itxtl1"));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/team/team1120")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTeam() throws Exception {
        when(teamUpdater.update(anyString(),any(Team.class))).thenReturn(TeamFixtures.UPDATED_TEAM_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/team/team1120")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(TeamFixtures.UPDATED_TEAM_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TeamFixtures.UPDATED_TEAM_BY_CODE_JSON));
    }

    @Test
    void addTeam() throws Exception {
        when(teamService.create(any(Team.class))).thenReturn(TeamFixtures.TEAM_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(TeamFixtures.TEAM_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TeamFixtures.TEAM_BY_CODE_JSON));
    }

    @Test
    void patchTeam() throws Exception {
        when(teamUpdater.patch(anyString(),any(Team.class))).thenReturn(TeamFixtures.UPDATED_TEAM_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/team/team1120")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(TeamFixtures.PATCHED_TEAM_BY_CODE_JSON))
                .andExpect(content().json(TeamFixtures.PATCHED_TEAM_BY_CODE_JSON));
    }

    @Test
    void getTeams() throws Exception {
        when(teamService.getAll()).thenReturn(TeamFixtures.TEAM_LIST);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/team")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(TeamFixtures.LIST_TEAM_JSON));
    }

    @TestConfiguration
    static class OpportunityControllerTestConfiguration {

        @Bean
        TeamMapper teamMapper(final ResolveService resolveService) {
            return new TeamMapperImpl(resolveService);
        }

        @Bean
        PeopleMapper peopleMapper(final CandidateMapper candidateMapper, final ResolveService resolveService) {
            return new PeopleMapperImpl(candidateMapper, resolveService);
        }

        @Bean
        CandidateMapper candidateMapper(final ResolveService resolveService) {
            return new CandidateMapperImpl(resolveService);
        }

        @Bean
        SkillMapper skillMapper(final PeopleMapper peopleMapper) {
            return new SkillMapperImpl(peopleMapper);
        }

        @MockBean
        TeamUpdater teamUpdater;

        @MockBean
        TeamService teamService;

        @MockBean
        SkillService skillService;

        @MockBean
        PositionService positionService;

        @MockBean
        PeopleService peopleService;
        @MockBean
        ProjectService projectService;

        @MockBean
        OfficeService officeService;

        @MockBean
        ClientService clientService;

        @Bean
        ResolveService resolveService(final SkillService skillService, final PositionService positionService,
                                      final PeopleService peopleService, final ProjectService projectService,
                                      final OfficeService officeService, final ClientService clientService) {
            return new ResolveService(skillService, positionService, peopleService, projectService, officeService, clientService);
        }
    }
}
