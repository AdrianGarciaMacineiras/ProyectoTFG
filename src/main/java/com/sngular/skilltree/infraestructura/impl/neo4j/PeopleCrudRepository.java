package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomPeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface PeopleCrudRepository extends Neo4jRepository<PeopleNode, String>, CustomPeopleRepository {

    PeopleNode findByCodeAndDeletedIsFalse(String personCode);

    PeopleNode findByEmployeeIdAndDeletedIsFalse(String personCode);

    PeopleNode findByEmployeeId(String personCode);
}
