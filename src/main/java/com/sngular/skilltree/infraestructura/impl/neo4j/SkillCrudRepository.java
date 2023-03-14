package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface SkillCrudRepository extends Neo4jRepository<SkillNode, String> {

    SkillNode findByCode(String skillcode);

    @Query("MATCH(n:Skill{code:$skillcode}) RETURN n")
    SkillNode findSkillByCode(String skillcode);

    @Query("MATCH p=()-[r:REQUIRE]->(n:Skill{code:$skillcode}) DELETE r")
    void deleteRequire(String skillcode);
}
