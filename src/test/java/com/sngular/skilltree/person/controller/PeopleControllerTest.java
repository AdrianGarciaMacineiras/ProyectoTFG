package com.sngular.skilltree.person.controller;

import static com.sngular.skilltree.person.fixtures.PersonFixtures.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@Slf4j
@WebMvcTest(controllers = PeopleController.class)
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
  void updatePerson() throws Exception {
    when(peopleService.update(anyString(),any(People.class))).thenReturn(UPDATED_PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders.put("/people/pc1120")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(PERSONDTO_BY_CODE_JSON))
            //.andDo(result -> log.info("-----" + result.getResponse().getStatus()));
            .andExpect(content().json(PERSONDTO_BY_CODE_JSON));
  }

  @Test
  void addPerson() throws Exception {
    when(peopleService.create(any(People.class))).thenReturn(UPDATED_PEOPLE_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders.post("/people")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content(PERSONDTO_BY_CODE_JSON))
            //.andDo(result -> log.info("-----" + result.getResponse().getStatus()));
            .andExpect(content().json(PERSONDTO_BY_CODE_JSON));
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