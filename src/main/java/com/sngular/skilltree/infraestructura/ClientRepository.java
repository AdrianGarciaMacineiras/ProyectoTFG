package com.sngular.skilltree.infraestructura;

import java.util.List;

import com.sngular.skilltree.model.Client;

public interface ClientRepository {

    List<Client> findAll();

    Client save(Client client);

    Client findByCode(String clientCode);

    boolean deleteByCode(String clientCode);

    List<Client> findByDeletedIsFalse();
}
