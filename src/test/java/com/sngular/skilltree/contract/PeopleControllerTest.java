package com.sngular.skilltree.contract;

import static com.sngular.skilltree.fixtures.PersonFixtures.LIST_PERSON_JSON;
import static com.sngular.skilltree.fixtures.PersonFixtures.PATCH_PERSON_BY_CODE_JSON;
import static com.sngular.skilltree.fixtures.PersonFixtures.PEOPLE_BY_CODE;
import static com.sngular.skilltree.fixtures.PersonFixtures.PEOPLE_LIST;
import static com.sngular.skilltree.fixtures.PersonFixtures.PERSON_BY_CODE_JSON;
import static com.sngular.skilltree.fixtures.PersonFixtures.UPDATED_PEOPLE_BY_CODE;
import static com.sngular.skilltree.fixtures.PersonFixtures.UPDATED_PERSON_BY_CODE_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
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
import com.sngular.skilltree.application.updater.PeopleUpdater;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.mapper.CandidateMapper;
import com.sngular.skilltree.contract.mapper.CandidateMapperImpl;
import com.sngular.skilltree.contract.mapper.PeopleMapper;
import com.sngular.skilltree.contract.mapper.PeopleMapperImpl;
import com.sngular.skilltree.contract.mapper.PositionMapper;
import com.sngular.skilltree.contract.mapper.PositionMapperImpl;
import com.sngular.skilltree.model.People;
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

@Slf4j
@WebMvcTest(controllers = PeopleController.class)
@Import(CommonTestConfiguration.class)
class PeopleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private PeopleUpdater peopleUpdater;

  @Autowired
  private PeopleService peopleService;

  @Test
  void getPersonByCode() throws Exception {
    when(peopleService.findByCode(anyString())).thenReturn(PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                    .get("/person/1")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().json(PERSON_BY_CODE_JSON));
  }

  @Test
  void shouldGetPersonByCodeFail() throws Exception {
    when(peopleService.findByCode(anyString())).thenThrow(new EntityNotFoundException("People", "5"));
    mockMvc.perform(MockMvcRequestBuilders
                    .get("/person/5")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  void shouldDeletePersonBySuccess() throws Exception{
    when(peopleService.deleteByCode(anyString())).thenReturn(true);
    mockMvc.perform(MockMvcRequestBuilders
                    .delete("/person/1")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
  }

  @Test
  void shouldDeletePersonFail() throws Exception{
    when(peopleService.deleteByCode(anyString())).thenThrow(new EntityNotFoundException("People", "1"));
    mockMvc.perform(MockMvcRequestBuilders
                    .delete("/person/1")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  void updatePerson() throws Exception {
    when(peopleUpdater.update(anyString(), any(People.class))).thenReturn(UPDATED_PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                    .put("/person/1")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content(PERSON_BY_CODE_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(UPDATED_PERSON_BY_CODE_JSON));
  }

  @Test
  void addPerson() throws Exception {
    when(peopleService.create(any(People.class))).thenReturn(PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                            .post("/people")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(PERSON_BY_CODE_JSON))
            .andExpect(content().json(PERSON_BY_CODE_JSON));
  }

  @Test
  void patchPerson() throws Exception {
    when(peopleUpdater.patch(anyString(), any(People.class))).thenReturn(UPDATED_PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                      .patch("/person/1")
                      .contentType(MediaType.APPLICATION_JSON)
                      .accept(MediaType.APPLICATION_JSON)
                      .content(PATCH_PERSON_BY_CODE_JSON))
           .andDo(log())
           .andExpect(content().json(PATCH_PERSON_BY_CODE_JSON));
  }

  @Test
  void getPeople() throws Exception {
    when(peopleService.getAll()).thenReturn(PEOPLE_LIST);
    mockMvc.perform(MockMvcRequestBuilders
                            .get("/people")
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().json(LIST_PERSON_JSON));
  }

  /*@Test
  void asignCandidate() throws Exception {
    when(peopleService.assignCandidate(anyLong(), anyString())).thenReturn(PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
            .post("/person/{peopleCode}/position/{positionCode}"))
  }*/

  @TestConfiguration
  static class ControllerTestConfiguration {

    @Bean
    public PeopleMapper getPeopleMapper(final ResolveService resolveService, final CandidateMapper candidateMapper) {
      return new PeopleMapperImpl(candidateMapper, resolveService);
    }

    @Bean
    public CandidateMapper candidateMapper(final ResolveService resolveService) {
      return new CandidateMapperImpl(resolveService);
    }

    @MockBean
    PeopleUpdater peopleUpdater;

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
    PositionMapper positionMapper(final ResolveService resolveService, final PeopleMapper peopleMapper, final CandidateMapper candidateMapper) {
      return new PositionMapperImpl(resolveService, peopleMapper, candidateMapper);
    }

    @Bean
    PeopleMapper peopleMapper(final CandidateMapper candidateMapper, final ResolveService resolveService) {
      return new PeopleMapperImpl(candidateMapper, resolveService);
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