package com.tfg.skilltree.application.updater.implement;

import java.util.Objects;

import com.tfg.skilltree.application.updater.ClientUpdater;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.contract.mapper.ClientMapper;
import com.tfg.skilltree.infraestructura.ClientRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.ClientCrudRepository;
import com.tfg.skilltree.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientUpdaterImpl implements ClientUpdater {

    private final ClientRepository clientRepository;

    private final ClientMapper mapper;

    private final ClientCrudRepository crud;

    @Override
    public Client update(String clientCode, Client newClient) {
        validate(clientCode);
        return clientRepository.save(newClient);
    }

    @Override
    public Client patch(String clientCode, Client patchedClient) {
        validate(clientCode);
        var oldClient = clientRepository.findByCode(clientCode);
        var client = mapper.patch(patchedClient, oldClient);
        return clientRepository.save(client);
    }

    private void validate(String code) {
        var oldClient = clientRepository.findByCode(code);
        if (Objects.isNull(oldClient) || oldClient.deleted()) {
            throw new EntityNotFoundException("Client", code);
        }
    }

}
