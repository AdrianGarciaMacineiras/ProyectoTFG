package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Client;

public interface ClientUpdater {

    Client update(final String clientCode, final Client newClient);

    Client patch(final String clientCode, final Client patchedClient);
}
