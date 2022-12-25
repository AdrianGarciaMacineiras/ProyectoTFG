package com.sngular.skilltree.contract.mapper;


import com.sngular.skilltree.api.model.ClientDTO;
import com.sngular.skilltree.api.model.PatchedClientDTO;
import com.sngular.skilltree.model.Client;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDTO toClientDTO(Client client);

    Client toClient(ClientDTO clientDTO);

    List<ClientDTO> toClientsDTO(Collection<Client> clients);

    Client toClient(PatchedClientDTO patchedClientDTO);

    void update(@MappingTarget Client oldClient, Client newClient);

    Client toClient(Object client);

}
