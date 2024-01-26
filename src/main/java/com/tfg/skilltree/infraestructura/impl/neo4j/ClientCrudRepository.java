package com.tfg.skilltree.infraestructura.impl.neo4j;

import java.util.List;

import com.tfg.skilltree.infraestructura.impl.neo4j.model.ClientNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ClientCrudRepository extends Neo4jRepository<ClientNode, String> {

    ClientNode findByCode(String clientCode);

    List<ClientNode> findByDeletedIsFalse();

    ClientNode findByName(String name);
}
