package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ClientNode;
import com.sngular.skilltree.model.Client;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfiguration.class)
public interface ClientNodeMapper {

    @InheritInverseConfiguration
    ClientNode toNode(Client client);

    Client fromNode(ClientNode clientNode);

    List<Client> map(List<ClientNode> all);
}
