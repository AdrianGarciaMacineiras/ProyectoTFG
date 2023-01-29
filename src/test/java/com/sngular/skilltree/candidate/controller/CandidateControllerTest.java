package com.sngular.skilltree.candidate.controller;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.CandidateController;
import com.sngular.skilltree.contract.mapper.CandidateMapper;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.People;
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
import static com.sngular.skilltree.person.fixtures.PersonFixtures.PERSONDTO_BY_CODE_JSON;
import static com.sngular.skilltree.person.fixtures.PersonFixtures.UPDATED_PEOPLE_BY_CODE;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@WebMvcTest(controllers = CandidateController.class)
final class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateService candidateService;

    @MockBean
    private ResolveService resolveService;

    @Test
    void getCandidateByCode() throws Exception {
        when(candidateService.findByCode(anyString())).thenReturn(CANDIDATE_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/candidate/pc1120")
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
        when(candidateService.update(anyString(),any(Candidate.class))).thenReturn(UPDATED_CANDIDATE_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/people/pc1120")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(PERSONDTO_BY_CODE_JSON))
                .andExpect(content().json(PERSONDTO_BY_CODE_JSON));
    }

    @TestConfiguration
    static class CandidateControllerTestConfiguration {

        @Bean
        CandidateMapper candidateMapper() {
            return Mappers.getMapper(CandidateMapper.class);
        }

    }
}
