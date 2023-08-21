package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import java.util.List;

import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomPeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PeopleView;
import com.sngular.skilltree.model.People;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PeopleRepositoryImpl implements PeopleRepository {

    private final PeopleCrudRepository crud;

    private final CustomPeopleRepository customCrud;

    private final PeopleNodeMapper mapper;

    private final Neo4jClient client;

    @Override
    public List<People> findAll() {
        return mapper.map(crud.findByDeletedIsFalse(PeopleView.class));
    }

    @Override
    public List<com.sngular.skilltree.model.views.PeopleView> findAllResumed() {
        return mapper.mapExtended(customCrud.getAllPeopleExtended());
    }

    @Override
    public People save(People people) {
        var peopleNode = mapper.toNode(people);
        return mapper.fromNode(crud.save(peopleNode));
    }

    @Override
    public People findByCode(String personCode) {
        final PeopleNode people;
        if (NumberUtils.isCreatable(personCode)) {
            people = crud.findByCodeAndDeletedIsFalse(personCode);
        } else {
            people = crud.findByEmployeeIdAndDeletedIsFalse(personCode);
        }
        return mapper.fromNode(people);
    }

    @Override
    public People findPeopleByCode(String personCode) {
        final PeopleNode people;
        if (NumberUtils.isCreatable(personCode)) {
            people = crud.findByCode(personCode, PeopleNode.class);
        } else {
            people = crud.findByEmployeeId(personCode);
        }
        return mapper.fromNode(people);
    }

    @Override
    public boolean deleteByCode(String personCode) {
        var node = crud.findByEmployeeId(personCode);
        node.setDeleted(true);
        crud.save(node);
        return true;
    }

    @Override
    public List<People> findByDeletedIsFalse() {
        return mapper.map(crud.findByDeletedIsFalse(PeopleView.class));
    }

    @Override
    public List<People> getPeopleSkills(List<String> skills) {

        var query = String.format("WITH %s as skills " +
                "MATCH(p:People)-[r:KNOWS]-(s:Skill) " +
                "WHERE s.code in skills " +
                "WITH p, size(skills) AS inputCnt, COUNT(DISTINCT s) AS cnt " +
                "WHERE cnt = inputCnt " +
                "RETURN p.code",skills);

        var peopleCodes = client.query(query).fetchAs(String.class).all();

        return peopleCodes
                .parallelStream()
                .map(code -> crud.findByCode(code, PeopleView.class))
                .map(mapper::fromView)
                .toList();

    }

    @Override
    public List<People> getOtherPeopleStrategicSkills(String teamcode) {

        var query = String.format("MATCH (p:People)-[k:KNOWS]-(s:Skill), (t:Team{code:'%s'}) " +
                "WHERE NOT (p)-[:MEMBER_OF]-(t) AND (s)-[:STRATEGIC]-(t) AND (p)-[:WORK_WITH]-(s) " +
                "RETURN p.code", teamcode);


         var peopleCodes = client
                 .query(query)
                 .fetchAs(String.class)
                .all();

        return peopleCodes
                .parallelStream()
                .map(code -> crud.findByCode(code, PeopleView.class))
                .map(mapper::fromView)
                .toList();
    }


}
