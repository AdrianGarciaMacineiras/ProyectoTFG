package com.sngular.skilltree.client.service;

import com.sngular.skilltree.application.ClientService;
import com.sngular.skilltree.application.ClientServiceImpl;
import com.sngular.skilltree.contract.mapper.ClientMapper;
import com.sngular.skilltree.infraestructura.ClientRepository;
import com.sngular.skilltree.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.sngular.skilltree.client.service.ClientFixtures.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    private ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @BeforeEach
    void setUp(){clientService = new ClientServiceImpl(clientRepository);}

    @Test
    @DisplayName("Testing save client")
    void testSave(){
        when(clientRepository.save(CLIENT_BY_CODE)).thenReturn(CLIENT_BY_CODE);
        Client result = clientService.create(CLIENT_BY_CODE);
        assertThat(result).isEqualTo(CLIENT_BY_CODE);
    }

    @Test
    @DisplayName("Testing getAll the clients")
    void testGetAll(){
        when(clientRepository.findAll()).thenReturn(CLIENT_LIST);
        List<Client> result = clientService.getAll();
        assertThat(result).containsExactly(CLIENT_BY_CODE, CLIENT2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a client")
    void testFindByCode(){
        when(clientRepository.findByCode(anyLong())).thenReturn(CLIENT_BY_CODE);
        Client result = clientService.findByCode(1L);
        assertThat(result).isEqualTo(CLIENT_BY_CODE);
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode(){
        when(clientRepository.deleteByCode(anyLong())).thenReturn(true);
        lenient().when(clientRepository.findByCode(anyLong())).thenReturn(CLIENT_BY_CODE);
        boolean result = clientService.deleteByCode(1L);
        assertThat(result).isTrue();
    }
}
