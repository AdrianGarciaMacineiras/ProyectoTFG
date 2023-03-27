package com.sngular.skilltree.application.updater.implement;

import com.sngular.skilltree.application.updater.ClientUpdater;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.mapper.ClientMapper;
import com.sngular.skilltree.infraestructura.ClientRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.ClientCrudRepository;
import com.sngular.skilltree.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientUpdaterImpl implements ClientUpdater {

    private final ClientRepository clientRepository;

    private final ClientMapper mapper;

    private final ClientCrudRepository crud;

    @Override
    public Client update(Long clientcode, Client newClient) {
        validate(clientcode);
        crud.detachDelete(clientcode);
        return clientRepository.save(newClient);
    }

    @Override
        public Client patch(Long clientcode, Client patchedClient) {
        validate(clientcode);
        var oldClient = clientRepository.findByCode(clientcode);
        var client = mapper.update(patchedClient, oldClient);
        crud.detachDelete(clientcode);
        return clientRepository.save(client);
    }

    private void validate(Long code) {
        var oldClient = clientRepository.findByCode(code);
        if (Objects.isNull(oldClient) || oldClient.deleted()) {
            throw new EntityNotFoundException("Client", code);
        }
    }

}
