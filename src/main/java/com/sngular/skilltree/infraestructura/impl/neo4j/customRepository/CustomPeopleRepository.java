package com.sngular.skilltree.infraestructura.impl.neo4j.customRepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomPeopleRepository {

    @Query("MATCH(n:People{code:$personcode}) RETURN n")
    PeopleNode findPeopleByCode(Long personcode);

}
