package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Client;

import java.util.List;

public interface ClientService {

    List<Client> getAll();

    Client create(final Client client);

    Client findByCode(final String clientcode);

    boolean deleteBeCode(final String clientcode);

    Client update(final String clientcode, final Client newClient);

    Client patch(final String clientcode, final Client patchedClient);
}
