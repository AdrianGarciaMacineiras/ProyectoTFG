package com.tfg.skilltree.contract;

import com.tfg.skilltree.CommonTestConfiguration;
import com.tfg.skilltree.application.updater.CandidateUpdater;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.contract.mapper.CandidateMapper;
import com.tfg.skilltree.contract.mapper.CandidateMapperImpl;
import com.tfg.skilltree.model.Candidate;
import com.tfg.skilltree.application.*;
import com.tfg.skilltree.fixtures.CandidateFixtures;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = CandidateController.class)
@Import(CommonTestConfiguration.class)
class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CandidateUpdater candidateUpdater;

    @Autowired
    private CandidateService candidateService;

    @Test
    void getCandidateByCode() throws Exception {
        when(candidateService.findByCode(anyString())).thenReturn(CandidateFixtures.CANDIDATE_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/candidate/c1120")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(CandidateFixtures.CANDIDATE_BY_CODE_JSON));
    }

    @Test
    void shouldGetCandidateByCodeFail() throws Exception {
        when(candidateService.findByCode(anyString())).thenThrow(new EntityNotFoundException("Candidate", "pc1124"));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/candidate/pc1124")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldDeleteCandidateBySuccess() throws Exception{
        when(candidateService.deleteByCode(anyString())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/candidate/pc1120")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldDeleteCandidateFail() throws Exception{
        when(candidateService.deleteByCode(anyString())).thenThrow(new EntityNotFoundException("Candidate", "pc1120"));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/candidate/pc1120")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateCandidate() throws Exception {
        when(candidateUpdater.update(anyString(),any(Candidate.class))).thenReturn(CandidateFixtures.UPDATED_CANDIDATE_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/candidate/c1120")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(CandidateFixtures.UPDATED_CANDIDATE_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(CandidateFixtures.UPDATED_CANDIDATE_BY_CODE_JSON));
    }
    @Test
    void patchCandidate() throws Exception{
        when(candidateUpdater.patch(anyString(),any(Candidate.class))).thenReturn(CandidateFixtures.UPDATED_CANDIDATE_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/api/candidate/c1120")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(CandidateFixtures.PATCH_CANDIDATE_BY_CODE_JSON))
                .andExpect(content().json(CandidateFixtures.PATCH_CANDIDATE_BY_CODE_JSON));
    }

    @TestConfiguration
    static class CandidateControllerTestConfiguration {

        @Bean
        CandidateMapper candidateMapper(final ResolveService resolveService) {
            return new CandidateMapperImpl(resolveService);
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
        ResolveService resolveService(
          final SkillService skillService, final PositionService positionService,
          final PeopleService peopleService, final ProjectService projectService,
          final OfficeService officeService, final ClientService clientService) {
            return new ResolveService(skillService, positionService, peopleService, projectService, officeService, clientService);
        }
    }
}
