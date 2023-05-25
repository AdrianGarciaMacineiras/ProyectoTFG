package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PeopleRepositoryImpl implements PeopleRepository {

    private final PeopleCrudRepository crud;

    private final PeopleNodeMapper mapper;

    private final Neo4jClient client;

    @Override
    public List<People> findAll() {
        return mapper.map(crud.findByDeletedIsFalse());
    }

    @Override
    public People save(People people) {
        var peopleNode = mapper.toNode(people);
        return mapper.fromNode(crud.save(peopleNode));
    }

    @Override
    public People findByCode(Long personcode) {
        return mapper.fromNode(crud.findByCodeAndDeletedIsFalse(personcode));
    }

    @Override
    public People findPeopleByCode(Long personcode) {
        return mapper.fromNode(crud.findPeopleByCode(personcode));
    }

    @Override
    public boolean deleteByCode(Long personcode) {
        var node = crud.findByCode(personcode);
        node.setDeleted(true);
        crud.save(node);
        return true;
    }

    @Override
    public List<People> findByDeletedIsFalse() {
        return mapper.map(crud.findByDeletedIsFalse());
    }

    @Override
    public List<People> getPeopleSkills(List<String> skills) {

        var query = String.format("WITH %s as skills " +
                "MATCH(p:People)-[r:KNOWS]-(s:Skill) " +
                "WHERE s.code in skills " +
                "WITH p, size(skills) AS inputCnt, COUNT(DISTINCT s) AS cnt " +
                "WHERE cnt = inputCnt " +
                "RETURN p.code",skills);

        var peopleCodes = client.query(query).fetchAs(Long.class).all();

        return peopleCodes
                .parallelStream()
                .map(crud::findByCode)
                .map(mapper::fromNode)
                .toList();

    }

    @Override
    public List<People> getOtherPeopleStrategicSkills(String teamcode) {

        var query = String.format("MATCH (p:People)-[k:KNOWS]-(s:Skill), (t:Team{code:'%s'}) " +
                "WHERE NOT (p)-[:MEMBER_OF]-(t) AND (s)-[:STRATEGIC]-(t) AND (p)-[:WORK_WITH]-(s) " +
                "RETURN p.code", teamcode);


         var peopleCodes = client
                 .query(query)
                .fetchAs(Long.class)
                .all();

        return peopleCodes
                .parallelStream()
                .map(crud::findByCode)
                .map(mapper::fromNode)
                .toList();
    }


}
