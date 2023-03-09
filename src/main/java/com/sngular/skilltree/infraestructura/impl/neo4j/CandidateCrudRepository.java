package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface CandidateCrudRepository extends Neo4jRepository<CandidateNode, String>{

    CandidateNode findByCode(String candidatecode);

    List<CandidateNode> findByDeletedIsFalse();
}
