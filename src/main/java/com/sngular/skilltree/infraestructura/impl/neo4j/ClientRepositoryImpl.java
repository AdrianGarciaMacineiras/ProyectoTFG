package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.ClientRepository;
import com.sngular.skilltree.model.Client;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.ClientNodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ClientRepositoryImpl implements ClientRepository {

    private final ClientCrudRepository crud;

    private final ClientNodeMapper mapper;

    @Override
    public List<Client> findAll() { return mapper.map(crud.findAll()); }

    @Override
    public Client save(Client client) {
        return mapper.fromNode(crud.save(mapper.toNode(client)));
    }

    @Override
    public Client findByCode(Long clientcode) {
        return mapper.fromNode(crud.findByCode(clientcode));
    }

    @Override
    public boolean deleteByCode(Long clientcode) {
        var node = crud.findByCode(clientcode);
        node.setDeleted(true);
        crud.save(node);
        return true;
    }

    @Override
    public List<Client> findByDeletedIsFalse() {
        return mapper.map(crud.findByDeletedIsFalse());
    }
}
