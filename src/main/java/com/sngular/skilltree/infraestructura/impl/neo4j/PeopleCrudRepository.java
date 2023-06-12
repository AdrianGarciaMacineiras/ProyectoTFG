package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomPeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface PeopleCrudRepository extends Neo4jRepository<PeopleNode, String>, CustomPeopleRepository {


    @Query("MATCH(n:People{code:$personCode}) RETURN n")
    PeopleNode findPeopleByCode(String personCode);

    PeopleNode findByCodeAndDeletedIsFalse(String personCode);
}
