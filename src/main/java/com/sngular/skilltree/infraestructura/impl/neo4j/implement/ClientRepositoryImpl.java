package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import java.util.List;

import com.sngular.skilltree.infraestructura.ClientRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.ClientCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.ClientNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ClientNode;
import com.sngular.skilltree.model.Client;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientCrudRepository crud;

    private final ClientNodeMapper mapper;

    @Override
    public List<Client> findAll() { return mapper.map(crud.findByDeletedIsFalse()); }

    @Override
    public Client save(Client client) {
        return mapper.fromNode(crud.save(mapper.toNode(client)));
    }

    @Override
    public Client findByCode(String clientCode) {
      ClientNode clientNode;
      if (NumberUtils.isCreatable(clientCode)) {
        clientNode = crud.findByCode(clientCode);
      } else {
        clientNode = crud.findByName(clientCode);
      }
      return mapper.fromNode(clientNode);
    }

    @Override
    public boolean deleteByCode(String clientCode) {
        var node = crud.findByCode(clientCode);
        node.setDeleted(true);
        crud.save(node);
        return true;
    }

    @Override
    public List<Client> findByDeletedIsFalse() {
        return mapper.map(crud.findByDeletedIsFalse());
    }
}
