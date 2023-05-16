package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import org.springframework.data.neo4j.core.schema.ElementId;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface CandidateCrudRepository extends Neo4jRepository<CandidateRelationship, ElementId>{

    CandidateRelationship findByCode(String candidatecode);

}
