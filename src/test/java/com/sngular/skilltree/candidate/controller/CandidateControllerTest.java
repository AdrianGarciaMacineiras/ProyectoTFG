package com.sngular.skilltree.candidate.controller;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.contract.CandidateController;
import com.sngular.skilltree.contract.mapper.CandidateMapper;
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
import static com.sngular.skilltree.candidate.fixtures.CandidateFixtures.CANDIDATE_BY_CODE;
import static com.sngular.skilltree.candidate.fixtures.CandidateFixtures.CANDIDATE_BY_CODE_JSON;
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

    @Test
    void getCandidateByCode() throws Exception {
        when(candidateService.findByCode(anyString())).thenReturn(CANDIDATE_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/candidate/pc1120")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(CANDIDATE_BY_CODE_JSON));
    }

    @TestConfiguration
    static class CandidateControllerTestConfiguration {

        @Bean
        CandidateMapper candidateMapper() {
            return Mappers.getMapper(CandidateMapper.class);
        }
    }
}
