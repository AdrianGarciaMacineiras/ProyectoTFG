package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customRepository.CustomCandidateRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface CandidateCrudRepository extends Neo4jRepository<CandidateRelationship, Long>, CustomCandidateRepository{

    CandidateRelationship findByCode(String candidatecode);

}
