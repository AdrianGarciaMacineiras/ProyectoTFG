package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.OfficeNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface OfficeCrudRepository extends Neo4jRepository<OfficeNode, String> {

  OfficeNode findByCode(String officeCode);
}
