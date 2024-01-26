package com.tfg.skilltree.application;

import java.util.List;

import com.tfg.skilltree.model.Client;

public interface ClientService {

    List<Client> getAll();

    Client create(final Client client);

    Client findByCode(final String clientCode);

    boolean deleteByCode(final String clientCode);
}
