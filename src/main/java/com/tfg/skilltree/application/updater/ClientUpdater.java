package com.tfg.skilltree.application.updater;

import com.tfg.skilltree.model.Client;

public interface ClientUpdater {

    Client update(final String clientCode, final Client newClient);

    Client patch(final String clientCode, final Client patchedClient);
}
