package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client create(final Client client);

    Client findByCode(final Long clientcode);

    boolean deleteByCode(final Long clientcode);
}
