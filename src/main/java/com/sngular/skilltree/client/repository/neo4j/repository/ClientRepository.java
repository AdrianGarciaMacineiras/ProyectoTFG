package com.sngular.skilltree.client.repository.neo4j.repository;


import com.sngular.skilltree.client.model.Client;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ClientRepository extends Neo4jRepository<Client, String> {


}
