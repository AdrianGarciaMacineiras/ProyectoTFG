package com.sngular.skilltree.infraestructura.impl.neo4j;

import java.util.List;
import java.util.Optional;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface PeopleCrudRepository extends Neo4jRepository<PeopleNode, String> {

    PeopleNode findByCodeAndDeletedIsFalse(String personCode);

    PeopleNode findByEmployeeIdAndDeletedIsFalse(String personCode);

    PeopleNode findByEmployeeId(String personCode);

    @Query("MATCH(p:People)-[r:COVER]-(n:Position) WHERE ID(r) = $id RETURN p")
    PeopleNode findByCoverId(String id);

    <T> List<T> findByDeletedIsFalse(Class<T> type);

  <T> Optional<T> findByCode(String code, Class<T> type);

}
