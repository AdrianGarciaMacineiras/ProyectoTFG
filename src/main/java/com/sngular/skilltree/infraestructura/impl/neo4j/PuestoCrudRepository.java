package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customRepository.CustomPuestoRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PuestoNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface PuestoCrudRepository extends Neo4jRepository<PuestoNode, String>, CustomPuestoRepository {

  PuestoNode findByCode(String opportunitycode);

  List<PuestoNode> findByDeletedIsFalse();
}
