package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.customRepository.CustomPeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface PeopleCrudRepository extends Neo4jRepository<PeopleNode, Long>, CustomPeopleRepository {


    PeopleNode findByCode(Long personcode);

    List<PeopleNode> findByDeletedIsFalse();

    PeopleNode findByCodeAndDeletedIsFalse(Long personCode);
}
