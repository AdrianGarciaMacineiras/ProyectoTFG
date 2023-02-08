package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.contract.mapper.ClientMapper;
import com.sngular.skilltree.infraestructura.ClientRepository;
import com.sngular.skilltree.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientUpdaterImpl implements ClientUpdater{

    private final ClientRepository clientRepository;

    private final ClientMapper mapper;
    @Override
    public Client update(String clientcode, Client newClient) {
        var oldClient = clientRepository.findByCode(clientcode);
        mapper.update(oldClient, newClient);
        return clientRepository.save(oldClient);
    }

    @Override
    public Client patch(String clientcode, Client patchedClient) {
        var oldClient = clientRepository.findByCode(clientcode);
        mapper.update(oldClient, patchedClient);
        return clientRepository.save(oldClient);
    }

}
