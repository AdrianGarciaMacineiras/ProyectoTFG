package com.sngular.skilltree.application;

import com.sngular.skilltree.infraestructura.ClientRepository;
import com.sngular.skilltree.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService{

    private final ClientRepository clientRepository;

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

    private void validate(Client client) {
    }
}
