package com.sngular.skilltree.contract;

import com.sngular.skilltree.application.*;
import com.sngular.skilltree.contract.mapper.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.sngular.skilltree.fixtures.SkillFixtures.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@Slf4j
@WebMvcTest(controllers = SkillController.class)
class SkillControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private SkillService skillService;

  @Test
  void getSkillByCode() throws Exception {
    when(skillService.findByCode(anyString())).thenReturn(SKILL_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                      .get("/skills/Spring")
                      .accept(MediaType.APPLICATION_JSON))
           .andExpect(content().json(SKILL_BY_CODE_JSON));
  }

  @Test
  void getSkills() throws Exception {
    when(skillService.getAll()).thenReturn(SKILL_LIST);
    mockMvc.perform(MockMvcRequestBuilders
                      .get("/skills")
                      .accept(MediaType.APPLICATION_JSON))
           .andExpect(content().json(LIST_SKILL_JSON));
  }

  @TestConfiguration
  static class OpportunityControllerTestConfiguration {

    @Bean
    SkillMapper skillMapper(final PeopleMapper peopleMapper) {
      return new SkillMapperImpl(peopleMapper);
    }

    @Bean
    PeopleMapper peopleMapper(final CandidateMapper candidateMapper, final ResolveService resolveService) {
      return new PeopleMapperImpl(candidateMapper, resolveService);
    }

    @Bean
    CandidateMapper candidateMapper(final ResolveService resolveService) {
      return new CandidateMapperImpl(resolveService);
    }

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
