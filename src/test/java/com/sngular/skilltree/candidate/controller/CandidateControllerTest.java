package com.sngular.skilltree.candidate.controller;

import com.sngular.skilltree.application.*;
import com.sngular.skilltree.application.updater.CandidateUpdater;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.CandidateController;
import com.sngular.skilltree.contract.mapper.*;
import com.sngular.skilltree.model.Candidate;
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

import static com.sngular.skilltree.candidate.fixtures.CandidateFixtures.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest(controllers = CandidateController.class)
class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CandidateUpdater candidateUpdater;

    @Autowired
    private CandidateService candidateService;

    @Test
    void getCandidateByCode() throws Exception {
        when(candidateService.findByCode(anyString())).thenReturn(CANDIDATE_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/candidate/c1120")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(CANDIDATE_BY_CODE_JSON));
    }

    @Test
    void shouldGetCandidateByCodeFail() throws Exception {
        when(candidateService.findByCode(anyString())).thenThrow(new EntityNotFoundException("Candidate", "pc1124"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/candidate/pc1124")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCandidateBySuccess() throws Exception{
        when(candidateService.deleteByCode(anyString())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/candidate/pc1120")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldDeleteCandidateFail() throws Exception{
        when(candidateService.deleteByCode(anyString())).thenThrow(new EntityNotFoundException("Candidate", "pc1120"));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/candidate/pc1120")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCandidate() throws Exception {
        when(candidateUpdater.update(anyString(),any(Candidate.class))).thenReturn(UPDATED_CANDIDATE_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/candidate/c1120")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(UPDATED_CANDIDATE_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(UPDATED_CANDIDATE_BY_CODE_JSON));
    }
    @Test
    void patchCandidate() throws Exception{
        when(candidateUpdater.patch(anyString(),any(Candidate.class))).thenReturn(UPDATED_CANDIDATE_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/candidate/c1120")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(PATCH_CANDIDATE_BY_CODE_JSON))
                .andExpect(content().json(PATCH_CANDIDATE_BY_CODE_JSON));
    }

    @Test
    void getCandidates() throws Exception{
        when(candidateService.getAll()).thenReturn(CANDIDATE_LIST);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/candidate")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(LIST_CANDIDATE_JSON));
    }

    @TestConfiguration
    static class CandidateControllerTestConfiguration {

        @Bean
        CandidateMapper candidateMapper() {
            return Mappers.getMapper(CandidateMapper.class);
        }

        @MockBean
        CandidateService candidateService;

        @MockBean
        CandidateUpdater candidateUpdater;

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
