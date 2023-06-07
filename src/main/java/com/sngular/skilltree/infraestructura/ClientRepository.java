package com.sngular.skilltree.infraestructura;


import com.sngular.skilltree.model.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> findAll();

    Client save(Client client);

    Client findByCode(String clientCode);

    boolean deleteByCode(String clientCode);

    List<Client> findByDeletedIsFalse();
}
