package com.sngular.skilltree.application;

import com.sngular.skilltree.contract.mapper.ClientMapper;
import com.sngular.skilltree.infraestructura.ClientRepository;
import com.sngular.skilltree.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

    private final ClientMapper mapper;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client create(Client client) {
        validate(client);
        return clientRepository.save(client);
    }

    @Override
    public Client findByCode(String clientcode) {
        return clientRepository.findByCode(clientcode);
    }

    @Override
    public boolean deleteByCode(String clientcode) {
        return clientRepository.deleteByCode(clientcode);
    }

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

    private void validate(Client client) {
    }
}
