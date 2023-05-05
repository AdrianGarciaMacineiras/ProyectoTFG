package com.sngular.skilltree.position.controller;

import static com.sngular.skilltree.position.fixtures.PositionFixtures.LIST_POSITION_JSON;
import static com.sngular.skilltree.position.fixtures.PositionFixtures.PATCH_POSITION_BY_CODE_JSON;
import static com.sngular.skilltree.position.fixtures.PositionFixtures.POSITION_BY_CODE;
import static com.sngular.skilltree.position.fixtures.PositionFixtures.POSITION_BY_CODE_JSON;
import static com.sngular.skilltree.position.fixtures.PositionFixtures.POSITION_LIST;
import static com.sngular.skilltree.position.fixtures.PositionFixtures.UPDATED_POSITION_BY_CODE;
import static com.sngular.skilltree.position.fixtures.PositionFixtures.UPDATED_POSITION_BY_CODE_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.sngular.skilltree.CommonTestConfiguration;
import com.sngular.skilltree.application.ClientService;
import com.sngular.skilltree.application.OfficeService;
import com.sngular.skilltree.application.PeopleService;
import com.sngular.skilltree.application.PositionService;
import com.sngular.skilltree.application.ProjectService;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.application.SkillService;
import com.sngular.skilltree.application.updater.PositionUpdater;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.PositionController;
import com.sngular.skilltree.contract.mapper.CandidateMapper;
import com.sngular.skilltree.contract.mapper.PeopleMapper;
import com.sngular.skilltree.contract.mapper.PositionMapper;
import com.sngular.skilltree.contract.mapper.SkillMapper;
import com.sngular.skilltree.model.Position;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
    PositionMapper opportunityMapper() {
      return Mappers.getMapper(PositionMapper.class);
    }

    @Bean
    SkillMapper skillMapper() {
      return Mappers.getMapper(SkillMapper.class);
    }

    @Bean
    PeopleMapper peopleMapper() {
      return Mappers.getMapper(PeopleMapper.class);
    }

    @Bean
    CandidateMapper candidateMapper() {
      return Mappers.getMapper(CandidateMapper.class);
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
