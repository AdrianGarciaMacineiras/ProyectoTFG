package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.ClientNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface ClientCrudRepository extends Neo4jRepository<ClientNode, Long>, CustomClientRepository {

    ClientNode findByCode(Long clientcode);

    List<ClientNode> findByDeletedIsFalse();
}
