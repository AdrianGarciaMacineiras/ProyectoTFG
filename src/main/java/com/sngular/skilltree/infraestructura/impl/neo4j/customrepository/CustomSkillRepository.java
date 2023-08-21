package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import java.util.List;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import org.springframework.data.neo4j.repository.query.Query;

public interface CustomSkillRepository {

    @Query("""
            MATCH (leaf:Skill)
            WHERE NOT ()-[:REQUIRED]->(leaf)
            RETURN leaf
            """)
    List<SkillNode> findAllLeafs();

}
