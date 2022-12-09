package com.sngular.skilltree.person.controller;

import static com.sngular.skilltree.person.fixtures.PersonFixtures.PERSON_BY_CODE;
import static com.sngular.skilltree.person.fixtures.PersonFixtures.PESON_BY_CODE_JSON;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sngular.skilltree.person.service.PersonService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class PersonControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private PersonService personService;

  private ObjectMapper mapper = new ObjectMapper();

  @Test
  void getPersonByCode() throws Exception {
    when(personService.findByCode(anyString())).thenReturn(PERSON_BY_CODE);
    mockMvc.perform(MockMvcRequestBuilders
                            .get("/person/pc1120")
                            .accept(MediaType.APPLICATION_JSON))
            .andExpect(content().json(PESON_BY_CODE_JSON));
  }

  @Test
  void deletePerson() {
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

  @Test
  void addPerson() {
  }
}