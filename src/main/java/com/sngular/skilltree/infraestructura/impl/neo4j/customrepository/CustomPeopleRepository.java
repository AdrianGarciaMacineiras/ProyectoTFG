package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import org.springframework.data.neo4j.repository.query.Query;


public interface CustomPeopleRepository {

    @Query("MATCH(n:People{code:$personCode}) RETURN n")
    PeopleNode findPeopleByCode(String personCode);

}
