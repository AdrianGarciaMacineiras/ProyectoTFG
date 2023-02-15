package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.model.Client;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Named("resolveUpdater")
public class ResolveUpdater {

    public Client updateClient(Client newClient){

        Client.ClientBuilder client = Client.builder();

        client.name(newClient.name());
        client.code(newClient.code());
        client.country(newClient.country());
        client.industry(newClient.industry());

        return client.build();
    }
}
