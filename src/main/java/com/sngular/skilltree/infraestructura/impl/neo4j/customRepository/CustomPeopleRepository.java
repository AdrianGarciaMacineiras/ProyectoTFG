package com.sngular.skilltree.infraestructura.impl.neo4j.customRepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomPeopleRepository {

    @Query("MATCH(n:People{code: $peopleCode}) DETACH DELETE n")
    void detachDelete(Long peopleCode);

    @Query("MATCH(n:People{code:$personcode}) RETURN n")
    PeopleNode findPeopleByCode(Long personcode);

    @Query("MATCH(n:People)-[r]-(p) WHERE p.code = $skillCode AND r.level IN $levelList RETURN n.code")
    List<Long> findCandidatesSkillList(String skillCode, List<String> levelList);

}
