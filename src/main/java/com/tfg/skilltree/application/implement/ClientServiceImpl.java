package com.tfg.skilltree.application.implement;

import java.util.List;
import java.util.Objects;
import com.tfg.skilltree.application.ClientService;
import com.tfg.skilltree.common.exceptions.EntityFoundException;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.infraestructura.ClientRepository;
import com.tfg.skilltree.model.Client;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    //@Cacheable(cacheNames = "clients")
    public List<Client> getAll() {
        return clientRepository.findAll();
    }

    @Override
    //@CacheEvict(cacheNames = "clients")
    public Client create(final Client client) {
        validateExist(client.code());
        return clientRepository.save(client);
    }

    @Override
    //@Cacheable(cacheNames = "clients")
    public Client findByCode(final String clientCode) {
        var client = clientRepository.findByCode(clientCode);
        if (Objects.isNull(client) || client.deleted())
            throw new EntityNotFoundException("Client", clientCode);
        return client;
    }

    @Override
    public boolean deleteByCode(final String clientCode) {
        validateDoesNotExist(clientCode);
        return clientRepository.deleteByCode(clientCode);
    }

    private void validateExist(final String code) {
        var oldClient = clientRepository.findByCode(code);
        if (!Objects.isNull(oldClient) && !oldClient.deleted()) {
            throw new EntityFoundException("Client", code);
        }
    }

    private void validateDoesNotExist(final String code) {
        var oldClient = clientRepository.findByCode(code);
        if (Objects.isNull(oldClient) || oldClient.deleted()) {
            throw new EntityNotFoundException("Client", code);
        }
    }
}
