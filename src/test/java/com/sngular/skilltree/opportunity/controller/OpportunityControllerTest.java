package com.sngular.skilltree.opportunity.controller;

import com.sngular.skilltree.application.OpportunityService;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.contract.OpportunityController;
import com.sngular.skilltree.contract.mapper.OpportunityMapper;
import com.sngular.skilltree.contract.mapper.SkillMapper;
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

import static com.sngular.skilltree.opportunity.fixtures.OpportunityFixtures.OPPORTUNITY_BY_CODE;
import static com.sngular.skilltree.opportunity.fixtures.OpportunityFixtures.OPPORTUNITY_BY_CODE_JSON;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Slf4j
@WebMvcTest(controllers = OpportunityController.class)
final class OpportunityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpportunityService opportunityService;

    @MockBean
    private ResolveService resolveService;

    @Test
    void getOpportunityByCode() throws Exception{
        when(opportunityService.findByCode(anyString())).thenReturn(OPPORTUNITY_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/opportunity/itxtl1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(OPPORTUNITY_BY_CODE_JSON));
    }


    @TestConfiguration
    static class OpportunityControllerTestConfiguration {

        @Bean
        OpportunityMapper opportunityMapper() {
            return Mappers.getMapper(OpportunityMapper.class);
        }

        @Bean
        SkillMapper skillMapper() {
            return Mappers.getMapper(SkillMapper.class);
        }
    }
}
