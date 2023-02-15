package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateNode;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomCandidateRepository {

    @Query("MATCH (p:People)-[k:KNOWS]-(sk) WHERE sk.code= ~$skillcode RETURN p")
    List<CandidateNode> findCandidatesBySkill(String skillcode);
}
