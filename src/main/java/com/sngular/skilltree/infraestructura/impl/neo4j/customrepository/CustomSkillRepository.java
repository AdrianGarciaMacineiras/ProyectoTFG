package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomSkillRepository {

    @Query("MATCH(p:Skill) WHERE (p)-[:REQUIRE]->(:Skill{code:'skills'}) RETURN p.code")
    List<String> findAllParents();

}
