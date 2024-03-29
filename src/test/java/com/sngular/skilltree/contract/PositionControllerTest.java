package com.sngular.skilltree.contract;

import com.sngular.skilltree.CommonTestConfiguration;
import com.sngular.skilltree.application.*;
import com.sngular.skilltree.application.updater.PositionUpdater;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.mapper.*;
import com.sngular.skilltree.model.Position;
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

import static com.sngular.skilltree.fixtures.PositionFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = PositionController.class)
@Import(CommonTestConfiguration.class)
class PositionControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PositionUpdater positionUpdater;

  @Autowired
  private PositionService positionService;

  @Test
  void getOpportunityByCode() throws Exception {
    when(positionService.findByCode(anyString())).thenReturn(POSITION_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                      .get("/position/itxtl1")
                      .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().is2xxSuccessful())
           .andExpect(content().json(POSITION_BY_CODE_JSON));
  }

  @Test
  void shouldDeleteOpportunityBySuccess() throws Exception {
    when(positionService.deleteByCode(anyString())).thenReturn(true);
    mockMvc.perform(MockMvcRequestBuilders
                      .delete("/position/itxtl1")
                      .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().is2xxSuccessful());
  }

  @Test
  void shouldDeleteOpportunityFail() throws Exception {
    when(positionService.deleteByCode(anyString())).thenThrow(new EntityNotFoundException("Opportunity", "itxtl1"));
    mockMvc.perform(MockMvcRequestBuilders
                      .delete("/position/itxtl1")
                      .accept(MediaType.APPLICATION_JSON))
           .andExpect(status().isNotFound());
  }

  @Test
  void updateOpportunity() throws Exception {
    when(positionUpdater.update(anyString(), any(Position.class))).thenReturn(UPDATED_POSITION_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                      .put("/position/itxtl1")
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON)
                      .content(UPDATED_POSITION_BY_CODE_JSON))
           .andExpect(status().isOk())
           .andExpect(content().json(UPDATED_POSITION_BY_CODE_JSON));
  }

  @Test
  void addOpportunity() throws Exception {
    when(positionService.create(any(Position.class))).thenReturn(POSITION_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                      .post("/position")
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON)
                      .content(POSITION_BY_CODE_JSON))
           .andExpect(status().isOk())
           .andExpect(content().json(POSITION_BY_CODE_JSON));
  }

  @Test
  void patchOpportunity() throws Exception {
    when(positionUpdater.patch(anyString(), any(Position.class))).thenReturn(UPDATED_POSITION_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                      .patch("/position/itxtl1")
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON)
                      .content(PATCH_POSITION_BY_CODE_JSON))
           .andExpect(content().json(PATCH_POSITION_BY_CODE_JSON));
  }

  @Test
  void getOpportunities() throws Exception {
    when(positionService.getAll()).thenReturn(POSITION_LIST);
    mockMvc.perform(MockMvcRequestBuilders
                      .get("/position")
                      .accept(MediaType.APPLICATION_JSON))
           .andExpect(content().json(LIST_POSITION_JSON));
  }

  @TestConfiguration
  static class OpportunityControllerTestConfiguration {

    @MockBean
    PositionUpdater positionUpdater;

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
    PositionMapper opportunityMapper(final ResolveService resolveService, final PeopleMapper peopleMapper, final CandidateMapper candidateMapper) {
      return new PositionMapperImpl(resolveService, peopleMapper, candidateMapper);
    }

    @Bean
    SkillMapper skillMapper(final PeopleMapper peopleMapper) {
      return new SkillMapperImpl(peopleMapper);
    }

    @Bean
    PeopleMapper peopleMapper(final ResolveService resolveService, final CandidateMapper candidateMapper) {
      return new PeopleMapperImpl(candidateMapper, resolveService);
    }

    @Bean
    CandidateMapper candidateMapper(final ResolveService resolveService) {
      return new CandidateMapperImpl(resolveService);
    }

    @Bean
    ResolveService resolveService(
      final SkillService skillService, final PositionService positionService,
      final PeopleService peopleService, final ProjectService projectService,
      final OfficeService officeService, final ClientService clientService) {
      return new ResolveService(skillService, positionService, peopleService, projectService, officeService, clientService);
    }
  }
}
