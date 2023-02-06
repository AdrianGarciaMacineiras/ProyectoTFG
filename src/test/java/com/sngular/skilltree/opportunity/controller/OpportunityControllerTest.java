package com.sngular.skilltree.opportunity.controller;

import com.sngular.skilltree.application.OpportunityService;
import com.sngular.skilltree.application.PeopleService;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.application.SkillService;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.OpportunityController;
import com.sngular.skilltree.contract.mapper.OpportunityMapper;
import com.sngular.skilltree.contract.mapper.SkillMapper;
import com.sngular.skilltree.model.Opportunity;
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

import static com.sngular.skilltree.opportunity.fixtures.OpportunityFixtures.*;
import static com.sngular.skilltree.opportunity.fixtures.OpportunityFixtures.OPPORTUNITY_BY_CODE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Slf4j
@WebMvcTest(controllers = OpportunityController.class)
final class OpportunityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OpportunityService opportunityService;

    @Test
    void getOpportunityByCode() throws Exception{
        when(opportunityService.findByCode(anyString())).thenReturn(OPPORTUNITY_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                                .get("/opportunity/itxtl1")
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(OPPORTUNITY_BY_CODE_JSON));
    }

    @Test
    void shouldDeleteOpportunityBySuccess() throws Exception{
        when(opportunityService.deleteByCode(anyString())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/opportunity/itxtl1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldDeleteCandidateFail() throws Exception{
        when(opportunityService.deleteByCode(anyString())).thenThrow(new EntityNotFoundException("OPportunity", "itxtl1"));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/opportunity/itxtl1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

   @Test
    void updateOpportunity() throws Exception {
        when(opportunityService.update(anyString(),any(Opportunity.class))).thenReturn(UPDATED_OPPORTUNITY_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/opportunity/itxtl1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(UPDATED_OPPORTUNITY_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(UPDATED_OPPORTUNITY_BY_CODE_JSON));
    }

    @Test
    void addCandidate() throws Exception {
        when(opportunityService.create(any(Opportunity.class))).thenReturn(OPPORTUNITY_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/opportunity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(OPPORTUNITY_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(OPPORTUNITY_BY_CODE_JSON));
    }

    @Test
    void patchOpportunity() throws Exception {
        when(opportunityService.patch(anyString(),any(Opportunity.class))).thenReturn(UPDATED_OPPORTUNITY_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/opportunity/itxtl1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(PATCH_OPPORTUNITY_BY_CODE_JSON))
                .andExpect(content().json(PATCH_OPPORTUNITY_BY_CODE_JSON));
    }

    @Test
    void getOpportunities() throws Exception {
        when(opportunityService.getAll()).thenReturn(OPPORTUNITY_LIST);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/opportunity")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(LIST_OPPORTUNITY_JSON));
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
