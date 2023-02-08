package com.sngular.skilltree.client.controller;

import com.sngular.skilltree.application.*;
import com.sngular.skilltree.application.updater.ClientUpdater;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.ClientController;
import com.sngular.skilltree.contract.mapper.ClientMapper;
import com.sngular.skilltree.contract.mapper.SkillMapper;
import com.sngular.skilltree.model.Client;
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

import static com.sngular.skilltree.client.fixtures.ClientFixtures.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest(controllers = ClientController.class)
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientUpdater clientUpdater;

    @Autowired
    private ClientService clientService;

    @Test
    void getClientByCode() throws Exception{
        when(clientService.findByCode(anyString())).thenReturn(CLIENT_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/client/itx")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(CLIENT_BY_CODE_JSON));
    }

    @Test
    void shouldDeleteClientBySuccess() throws Exception{
        when(clientService.deleteByCode(anyString())).thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/client/itx")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldDeleteClientFail() throws Exception{
        when(clientService.deleteByCode(anyString())).thenThrow(new EntityNotFoundException("Client", "itx"));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/client/itx")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateClient() throws Exception {
        when(clientUpdater.update(anyString(),any(Client.class))).thenReturn(UPDATED_CLIENT_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/client/itx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(UPDATED_CLIENT_BY_CODE_JSON))
                .andExpect(content().json(UPDATED_CLIENT_BY_CODE_JSON));
    }

    @Test
    void addClient() throws Exception {
        when(clientService.create(any(Client.class))).thenReturn(CLIENT_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(CLIENT_BY_CODE_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(CLIENT_BY_CODE_JSON));
    }

    @Test
    void patchClient() throws Exception {
        when(clientUpdater.patch(anyString(),any(Client.class))).thenReturn(UPDATED_CLIENT_BY_CODE);
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/client/itx")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(PATCH_CLIENT_BY_CODE_JSON))
                .andExpect(content().json(PATCH_CLIENT_BY_CODE_JSON));
    }

    @Test
    void getClients() throws Exception {
        when(clientService.getAll()).thenReturn(CLIENT_LIST);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/client")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(content().json(LIST_CLIENT_JSON));
    }

    @TestConfiguration
    static class OpportunityControllerTestConfiguration {

        @Bean
        ClientMapper clientMapper() {
            return Mappers.getMapper(ClientMapper.class);
        }

        @Bean
        SkillMapper skillMapper() {
            return Mappers.getMapper(SkillMapper.class);
        }

        @MockBean
        ClientService clientService;

        @MockBean
        ClientUpdater clientUpdater;

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
