package com.sngular.skilltree.contract.mapper;


import com.sngular.skilltree.api.model.ClientDTO;
import com.sngular.skilltree.api.model.PatchedClientDTO;
import com.sngular.skilltree.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source="HQ", target="hQ")
    ClientDTO toClientDTO(Client client);

    Client toClient(ClientDTO clientDTO);

    List<ClientDTO> toClientsDTO(Collection<Client> clients);

    Client toClient(PatchedClientDTO patchedClientDTO);


    //void update(@MappingTarget Client oldClient, Client newClient);

    @Named("update")
    default Client update(Client newClient, Client oldClient) {
        Client.ClientBuilder clientBuilder = oldClient.toBuilder();

        Client client = clientBuilder
                .code(oldClient.code())
                .industry((newClient.industry() == null) ? oldClient.industry() : newClient.industry())
                .country((newClient.country() == null) ? oldClient.country() : newClient.country())
                .HQ((newClient.HQ() == null) ? oldClient.HQ() : newClient.HQ())
                .offices((newClient.offices() == null) ? oldClient.offices() : newClient.offices())
                .principalOffice((newClient.principalOffice() == null) ? oldClient.principalOffice() : newClient.principalOffice())
                .name((newClient.name() == null) ? oldClient.name() : newClient.name())
                .build();

        return client;
    };

    Client toClient(Object client);

}
