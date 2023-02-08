package com.sngular.skilltree.team.controller;

import com.sngular.skilltree.application.*;
import com.sngular.skilltree.application.updater.TeamUpdater;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.TeamController;
import com.sngular.skilltree.contract.mapper.OpportunityMapper;
import com.sngular.skilltree.contract.mapper.SkillMapper;
import com.sngular.skilltree.contract.mapper.TeamMapper;
import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.sngular.skilltree.team.fixtures.TeamFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = TeamController.class)
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeamUpdater teamUpdater;

    @Autowired
    private TeamService teamService;

    @Test
    void getTeamByCode() throws Exception{
        when(teamService.findByCode(anyString())).thenReturn(TEAM_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/team/team1120")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(TEAM_BY_CODE_JSON));
    }

    @Test
    void shouldDeleteTeamBySuccess() throws Exception{
        when(teamService.deleteByCode(anyString())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/team/team1120")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldDeleteTeamFail() throws Exception{
        when(teamService.deleteByCode(anyString())).thenThrow(new EntityNotFoundException("Opportunity", "itxtl1"));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/team/team1120")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateTeam() throws Exception {
        when(teamUpdater.update(anyString(),any(Team.class))).thenReturn(UPDATED_TEAM_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/team/team1120")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(UPDATED_TEAM_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(UPDATED_TEAM_BY_CODE_JSON));
    }

    @Test
    void addTeam() throws Exception {
        when(teamService.create(any(Team.class))).thenReturn(TEAM_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(TEAM_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(TEAM_BY_CODE_JSON));
    }

    @Test
    void patchTeam() throws Exception {
        when(teamUpdater.patch(anyString(),any(Team.class))).thenReturn(UPDATED_TEAM_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/team/team1120")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(PATCHED_TEAM_BY_CODE_JSON))
                .andExpect(content().json(PATCHED_TEAM_BY_CODE_JSON));
    }

    @Test
    void getTeams() throws Exception {
        when(teamService.getAll()).thenReturn(TEAM_LIST);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/team")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(LIST_TEAM_JSON));
    }

    @TestConfiguration
    static class OpportunityControllerTestConfiguration {

        @Bean
        TeamMapper teamMapper() {
            return Mappers.getMapper(TeamMapper.class);
        }

        @Bean
        SkillMapper skillMapper() {
            return Mappers.getMapper(SkillMapper.class);
        }

        @MockBean
        TeamUpdater teamUpdater;

        @MockBean
        TeamService teamService;

        @MockBean
        SkillService skillService;

        @MockBean
        OpportunityService opportunityService;

        @MockBean
        PeopleService peopleService;

        @Bean
        ResolveService resolveService(final SkillService skillService, final OpportunityService opportunityService, final PeopleService peopleService) {
            return new ResolveService(skillService, opportunityService, peopleService);
        }
    }
}
