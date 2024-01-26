package com.tfg.skilltree.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.util.List;

import com.tfg.skilltree.application.implement.ClientServiceImpl;
import com.tfg.skilltree.common.exceptions.EntityFoundException;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.contract.mapper.ClientMapper;
import com.tfg.skilltree.infraestructura.ClientRepository;
import com.tfg.skilltree.model.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientService clientService;

    private final ClientMapper mapper = Mappers.getMapper(ClientMapper.class);

    @BeforeEach
    void setUp() {clientService = new ClientServiceImpl(clientRepository);}

    @Test
    @DisplayName("Testing save client")
    void testSave(){
        when(clientRepository.save(ClientFixtures.CLIENT_BY_CODE)).thenReturn(ClientFixtures.CLIENT_BY_CODE);
        Client result = clientService.create(ClientFixtures.CLIENT_BY_CODE);
        assertThat(result).isEqualTo(ClientFixtures.CLIENT_BY_CODE);
    }

    @Test
    @DisplayName("Testing save client exception already exists")
    void testSaveException(){
        when(clientRepository.findByCode(anyString())).thenReturn(ClientFixtures.CLIENT_BY_CODE);
        Assertions.assertThrows(EntityFoundException.class, () ->
                clientService.create(ClientFixtures.CLIENT_BY_CODE)
        );
    }

    @Test
    @DisplayName("Testing getAll the clients")
    void testGetAll(){
        when(clientRepository.findAll()).thenReturn(ClientFixtures.CLIENT_LIST);
        List<Client> result = clientService.getAll();
        assertThat(result).containsExactly(ClientFixtures.CLIENT_BY_CODE, ClientFixtures.CLIENT2_BY_CODE);
    }

    @Test
    @DisplayName("Testing findByCode a client")
    void testFindByCode() {
        when(clientRepository.findByCode(anyString())).thenReturn(ClientFixtures.CLIENT_BY_CODE);
        Client result = clientService.findByCode("1");
        assertThat(result).isEqualTo(ClientFixtures.CLIENT_BY_CODE);
    }

    @Test
    @DisplayName("Testing find by code exception null")
    void testFindByCodeExceptionNull(){
        when(clientRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                clientService.findByCode("1")
        );
    }

    @Test
    @DisplayName("Testing find by code exception deleted")
    void testFindByCodeExceptionDeletedTrue(){
        when(clientRepository.findByCode(anyString())).thenReturn(ClientFixtures.CLIENT_BY_CODE_DELETED_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                clientService.findByCode("1")
        );
    }

    @Test
    @DisplayName("Testing deleteByCode")
    void testDeleteByCode() {
        when(clientRepository.deleteByCode(anyString())).thenReturn(true);
        lenient().when(clientRepository.findByCode(anyString())).thenReturn(ClientFixtures.CLIENT_BY_CODE);
        boolean result = clientService.deleteByCode("1");
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Testing delete client exception null")
    void testDeleteExceptionNull(){
        when(clientRepository.findByCode(anyString())).thenReturn(null);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                clientService.deleteByCode("1")
        );
    }

    @Test
    @DisplayName("Testing delete client exception deleted")
    void testDeleteExceptionDeletedTrue(){
        when(clientRepository.findByCode(anyString())).thenReturn(ClientFixtures.CLIENT_BY_CODE_DELETED_TRUE);
        Assertions.assertThrows(EntityNotFoundException.class, () ->
                clientService.deleteByCode("1")
        );
    }
}
