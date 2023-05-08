package com.sngular.skilltree.contract;

import static com.sngular.skilltree.fixtures.PersonFixtures.LIST_PERSON_JSON;
import static com.sngular.skilltree.fixtures.PersonFixtures.PATCH_PERSON_BY_CODE_JSON;
import static com.sngular.skilltree.fixtures.PersonFixtures.PEOPLE_BY_CODE;
import static com.sngular.skilltree.fixtures.PersonFixtures.PEOPLE_LIST;
import static com.sngular.skilltree.fixtures.PersonFixtures.PERSON_BY_CODE_JSON;
import static com.sngular.skilltree.fixtures.PersonFixtures.UPDATED_PEOPLE_BY_CODE;
import static com.sngular.skilltree.fixtures.PersonFixtures.UPDATED_PERSON_BY_CODE_JSON;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
import com.sngular.skilltree.application.updater.PeopleUpdater;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.mapper.CandidateMapper;
import com.sngular.skilltree.contract.mapper.PeopleMapper;
import com.sngular.skilltree.contract.mapper.SkillMapper;
import com.sngular.skilltree.model.People;
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
    when(peopleService.findByCode(anyLong())).thenReturn(PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                            .get("/people/1")
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().json(PERSON_BY_CODE_JSON));
  }

  @Test
  void shouldGetPersonByCodeFail() throws Exception {
    when(peopleService.findByCode(anyLong())).thenThrow(new EntityNotFoundException("People", "5"));
    mockMvc.perform(MockMvcRequestBuilders
                    .get("/people/5")
                    .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  void shouldDeletePersonBySuccess() throws Exception{
    when(peopleService.deleteByCode(anyLong())).thenReturn(true);
    mockMvc.perform(MockMvcRequestBuilders
                            .delete("/people/1")
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
  }

  @Test
  void shouldDeletePersonFail() throws Exception{
    when(peopleService.deleteByCode(anyLong())).thenThrow(new EntityNotFoundException("People", "1"));
    mockMvc.perform(MockMvcRequestBuilders
                            .delete("/people/1")
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  void updatePerson() throws Exception {
    when(peopleUpdater.update(anyLong(), any(People.class))).thenReturn(UPDATED_PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                      .put("/people/1")
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
    when(peopleUpdater.patch(anyLong(),any(People.class))).thenReturn(UPDATED_PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                            .patch("/people/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(PATCH_PERSON_BY_CODE_JSON))
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

  @TestConfiguration
  static class ControllerTestConfiguration {

    @Bean
    public PeopleMapper getPeopleMapper() {
      return Mappers.getMapper(PeopleMapper.class);
    }

    @Bean
    public SkillMapper getSkillMapper() {
      return Mappers.getMapper(SkillMapper.class);
    }

    @Bean
    public CandidateMapper candidateMapper() {
      return Mappers.getMapper(CandidateMapper.class);
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
    ResolveService resolveService(
      final SkillService skillService, final PositionService positionService,
      final PeopleService peopleService, final ProjectService projectService,
      final OfficeService officeService, final ClientService clientService) {
      return new ResolveService(skillService, positionService, peopleService, projectService, officeService, clientService);
    }
  }
}