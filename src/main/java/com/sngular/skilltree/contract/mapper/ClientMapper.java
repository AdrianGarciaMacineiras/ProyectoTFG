package com.sngular.skilltree.contract.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.api.model.ClientDTO;
import com.sngular.skilltree.api.model.PatchedClientDTO;
import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = CommonMapperConfiguration.class)
public interface ClientMapper {

    @Mapping(source="HQ", target="hQ")
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
                .HQ((Objects.isNull(newClient.HQ())) ? oldClient.HQ() : newClient.HQ())
                .offices((Objects.isNull(newClient.offices())) ? oldClient.offices() : newClient.offices())
                .principalOffice((Objects.isNull(newClient.principalOffice())) ? oldClient.principalOffice() : newClient.principalOffice())
                .name((Objects.isNull(newClient.name())) ? oldClient.name() : newClient.name())
                .build();

    }
}
