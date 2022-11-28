package com.sngular.skilltree.client.repository.neo4j.repository;


import com.sngular.skilltree.client.model.Client;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;

public interface ClientRepository extends ReactiveNeo4jRepository<Client, String> {


}
