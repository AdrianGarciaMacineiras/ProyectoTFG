package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomPeopleRepository {

    @Query("MATCH(n:People{code: $peopleCode}) DETACH DELETE n")
    void detachDelete(Long peopleCode);

    @Query("MATCH(n:People)-[r:KNOWS]-(s:Skill{code:$skillcode}) RETURN n")
    List<PeopleNode> findCandidatesKnowsLow(String skillcode);

    @Query("MATCH(n:People)-[r:KNOWS]-(s:Skill{code:$skillcode}) WHERE r.level= MEDIUM AND r.level = HIGH RETURN n")
    List<PeopleNode> findCandidatesKnowsMedium(String skillcode);

    @Query("MATCH(n:People)-[r:KNOWS]-(s:Skill{code:$skillcode}) WHERE r.level = HIGH RETURN n")
    List<PeopleNode> findCandidatesKnowsHigh(String skillcode);

    @Query("MATCH(n:People)-[r:KNOWS]-(p:Skill) where p.code IN $skillCodeList RETURN n,r,p")
    List<PeopleNode> findCandidatesSkillList(List<String> skillCodeList);
}
