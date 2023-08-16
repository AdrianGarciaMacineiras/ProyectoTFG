package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface PeopleCrudRepository extends Neo4jRepository<PeopleNode, String> {

    PeopleNode findByCodeAndDeletedIsFalse(String personCode);

    PeopleNode findByEmployeeIdAndDeletedIsFalse(String personCode);

    PeopleNode findByEmployeeId(String personCode);

    <T> List<T> findByDeletedIsFalse(Class<T> type);

    <T> T findByCode(String code, Class<T> type);

}
