package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SkillCrudRepository extends Neo4jRepository<SkillNode, Long> {

    SkillNode findByCode(String skillcode);
}
