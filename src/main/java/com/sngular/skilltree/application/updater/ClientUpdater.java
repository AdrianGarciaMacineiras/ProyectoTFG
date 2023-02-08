package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Client;

public interface ClientUpdater {

    Client update(final String clientcode, final Client newClient);

    Client patch(final String clientcode, final Client patchedClient);
}
