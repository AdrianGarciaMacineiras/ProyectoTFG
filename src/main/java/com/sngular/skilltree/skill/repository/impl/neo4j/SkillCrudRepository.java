package com.sngular.skilltree.skill.repository.impl.neo4j;

import com.sngular.skilltree.skill.repository.impl.neo4j.model.SkillNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface SkillCrudRepository extends Neo4jRepository<SkillNode, String> {

    SkillNode findByCode(String skillcode);
}
