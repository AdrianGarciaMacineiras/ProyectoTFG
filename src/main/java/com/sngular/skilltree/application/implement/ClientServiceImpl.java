package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.ClientService;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.ClientRepository;
import com.sngular.skilltree.model.Client;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
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
    public Client findByCode(Long clientcode) {
        var client = clientRepository.findByCode(clientcode);
        if (Objects.isNull(client) || client.deleted())
            throw new EntityNotFoundException("Client", clientcode);
        return client;
    }

    @Override
    public boolean deleteByCode(Long clientcode) {
        validateDoesNotExist(clientcode);
        return clientRepository.deleteByCode(clientcode);
    }


    private void validateExist(Long code) {
        var oldClient = clientRepository.findByCode(code);
        if (!Objects.isNull(oldClient) && !oldClient.deleted()) {
            throw new EntityFoundException("Client", code);
        }
    }

    private void validateDoesNotExist(Long code) {
        var oldClient = clientRepository.findByCode(code);
        if (Objects.isNull(oldClient) || oldClient.deleted()) {
            throw new EntityNotFoundException("Client", code);
        }
    }
}
