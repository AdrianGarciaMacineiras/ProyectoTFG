package com.tfg.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.tfg.skilltree.api.model.ClientDTO;
import com.tfg.skilltree.api.model.PatchedClientDTO;
import com.tfg.skilltree.common.config.CommonMapperConfiguration;
import com.tfg.skilltree.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = CommonMapperConfiguration.class)
public interface ClientMapper {

    ClientDTO toClientDTO(Client client);

    Client toClient(ClientDTO clientDTO);

    List<ClientDTO> toClientsDTO(Collection<Client> clients);

    Client toClient(PatchedClientDTO patchedClientDTO);

    @Named("patch")
    default Client patch(Client newClient, Client oldClient) {
        Client.ClientBuilder clientBuilder = oldClient.toBuilder();

        return clientBuilder
                .code(oldClient.code())
                .industry((Objects.isNull(newClient.industry())) ? oldClient.industry() : newClient.industry())
                .country((Objects.isNull(newClient.country())) ? oldClient.country() : newClient.country())
                .offices((Objects.isNull(newClient.offices())) ? oldClient.offices() : newClient.offices())
                .name((Objects.isNull(newClient.name())) ? oldClient.name() : newClient.name())
                .build();

    }
}