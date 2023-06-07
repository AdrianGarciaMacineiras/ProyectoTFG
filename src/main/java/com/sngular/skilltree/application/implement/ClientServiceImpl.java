package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.ClientService;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.ClientRepository;
import com.sngular.skilltree.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client create(Client client) {
        validateExist(client.code());
        return clientRepository.save(client);
    }

    @Override
    public Client findByCode(String clientCode) {
        var client = clientRepository.findByCode(clientCode);
        if (Objects.isNull(client) || client.deleted())
            throw new EntityNotFoundException("Client", clientCode);
        return client;
    }

    @Override
    public boolean deleteByCode(String clientCode) {
        validateDoesNotExist(clientCode);
        return clientRepository.deleteByCode(clientCode);
    }


    private void validateExist(String code) {
        var oldClient = clientRepository.findByCode(code);
        if (!Objects.isNull(oldClient) && !oldClient.deleted()) {
            throw new EntityFoundException("Client", code);
        }
    }

    private void validateDoesNotExist(String code) {
        var oldClient = clientRepository.findByCode(code);
        if (Objects.isNull(oldClient) || oldClient.deleted()) {
            throw new EntityNotFoundException("Client", code);
        }
    }
}
