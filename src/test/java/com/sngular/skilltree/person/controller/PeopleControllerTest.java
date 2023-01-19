package com.sngular.skilltree.person.controller;

import static com.sngular.skilltree.person.fixtures.PersonFixtures.PEOPLE_BY_CODE;
import static com.sngular.skilltree.person.fixtures.PersonFixtures.PESON_BY_CODE_JSON;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sngular.skilltree.application.PeopleService;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.PeopleController;
import com.sngular.skilltree.contract.mapper.PeopleMapper;
import com.sngular.skilltree.contract.mapper.SkillMapper;
import com.sngular.skilltree.model.People;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PeopleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PeopleService peopleService;

  @Test
  void getPersonByCode() throws Exception {
    when(peopleService.findByCode(anyString())).thenReturn(PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                            .get("/people/pc1120")
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().json(PERSON_BY_CODE_JSON));
  }

  @Test
  void shouldDeletePersonBySuccess() throws Exception{
    when(peopleService.deleteByCode(anyString())).thenReturn(true);
    mockMvc.perform(MockMvcRequestBuilders
                            .delete("/people/pc1120")
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().is2xxSuccessful());
  }
  @Test
  void shouldDeletePersonFail() throws Exception{

    when(peopleService.deleteByCode(anyString())).thenThrow(new EntityNotFoundException("People", "pc1120"));
    mockMvc.perform(MockMvcRequestBuilders
                            .delete("/people/pc1120")
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
  }

  @Test
  void updatePerson() {
  }

  @Test
  void patchPerson() {
  }

  @Test
  void getPeople() {
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

    @MockBean
    public ResolveService resolveService;
  }
}