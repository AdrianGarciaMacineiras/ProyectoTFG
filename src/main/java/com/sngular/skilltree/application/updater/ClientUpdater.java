package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Client;

public interface ClientUpdater {

    Client update(final Integer clientcode, final Client newClient);

    Client patch(final Integer clientcode, final Client patchedClient);
}
