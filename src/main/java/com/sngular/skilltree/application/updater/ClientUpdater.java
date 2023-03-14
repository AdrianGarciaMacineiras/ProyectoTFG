package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Client;

public interface ClientUpdater {

    Client update(final Long clientcode, final Client newClient);

    Client patch(final Long clientcode, final Client patchedClient);
}
