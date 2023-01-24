package com.sngular.skilltree.candidate.controller;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.contract.CandidateController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.sngular.skilltree.candidate.fixtures.CandidateFixtures.CANDIDATE_BY_CODE;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@Slf4j
@WebMvcTest(controllers = CandidateController.class)
public final class CandidateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CandidateService candidateService;

    @Test
    void getCandidateByCode(){
        when(candidateService.findByCode(anyString())).thenReturn(CANDIDATE_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/candidate/c1120")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(CANDIDATE_BY_CODE_JSON));
    }
}
