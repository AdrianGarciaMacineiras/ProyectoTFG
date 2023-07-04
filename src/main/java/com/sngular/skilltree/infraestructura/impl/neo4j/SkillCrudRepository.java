package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomSkillRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface SkillCrudRepository extends Neo4jRepository<SkillNode, String>, CustomSkillRepository {

    SkillNode findByCode(String skillCode);

    @Query("MATCH(n:Skill{code:$skillCode}) RETURN n")
    SkillNode findSkillByCode(String skillCode);

    @Query("MATCH p=()-[r:REQUIRE]->(n:Skill{code:$skillCode}) DELETE r")
    void deleteRequire(String skillCode);

    SkillNode findByName(String skillName);
}
