package com.sngular.skilltree.infraestructura.impl.neo4j.customRepository;

import org.springframework.data.neo4j.repository.query.Query;

public interface CustomClientRepository {

    @Query("MATCH(n:Client{code: $clientCode}) DETACH DELETE n")
    void detachDelete(Long clientCode);
}
