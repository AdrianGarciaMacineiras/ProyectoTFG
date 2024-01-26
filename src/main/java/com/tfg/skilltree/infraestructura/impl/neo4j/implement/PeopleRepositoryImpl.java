package com.tfg.skilltree.infraestructura.impl.neo4j.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.tfg.skilltree.infraestructura.PeopleRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.customrepository.CustomPeopleRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.tfg.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.tfg.skilltree.infraestructura.impl.neo4j.querymodel.PeopleView;
import com.tfg.skilltree.model.People;
import com.tfg.skilltree.model.PeopleSkill;
import com.tfg.skilltree.model.views.PeopleNamesView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class PeopleRepositoryImpl implements PeopleRepository {

    private static final List<String> LOW_LEVEL_LIST = List.of("'low'", "'middle'", "'advanced'");

    private static final List<String> MID_LEVEL_LIST = List.of("'middle'", "'advanced'");

    private static final List<String> HIGH_LEVEL_LIST = List.of("'advanced'");

    private final PeopleCrudRepository crud;

    private final CustomPeopleRepository customCrud;

    private final PeopleNodeMapper mapper;

    private final Neo4jClient client;

    @Override
    public List<People> findAll() {
        return mapper.map(crud.findByDeletedIsFalse(PeopleView.class));
    }

    @Override
    public List<com.tfg.skilltree.model.views.PeopleView> findAllResumed() {
        return mapper.mapExtended(customCrud.getAllPeopleExtended());
    }

    @Override
    public List<PeopleNamesView> findAllNames() {
        return mapper.mapPeopleNames(customCrud.getAllPeopleNames());
    }

    @Override
    public People save(People people) {
        var peopleNode = mapper.toNode(people);
        return mapper.fromNode(crud.save(peopleNode));
    }

    @Override
    public People findByCode(String personCode) {
        PeopleNode people;
        if (NumberUtils.isCreatable(personCode)) {
            people = crud.findByCodeAndDeletedIsFalse(personCode);
        } else {
            people = crud.findByEmployeeIdAndDeletedIsFalse(personCode);
        }
        if (Objects.nonNull(people)) {
            for (var cover : people.getAssigns()) {
                if (Objects.isNull(cover.positionNode().getProject())) {
                    log.warn(String.format("Position: %s without project", cover.positionNode().getCode()));
                }
            }
        } else {
            return null;
        }
        return mapper.fromNode(people);
    }

    @Override
    public People findPeopleByCode(String personCode) {
        final PeopleNode people;
        if (NumberUtils.isCreatable(personCode)) {
            people = crud.findByCode(personCode, PeopleNode.class).orElse(null);
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
    public List<People> getPeopleBySkills(List<PeopleSkill> skills) {

        final var filter = new ArrayList<String>();
        for (var positionSkill : skills) {
            switch (positionSkill.level()) {
                case LOW -> filter.add(fillFilterBuilder(positionSkill, LOW_LEVEL_LIST));
                case MIDDLE -> filter.add(fillFilterBuilder(positionSkill, MID_LEVEL_LIST));
                default -> filter.add(fillFilterBuilder(positionSkill, HIGH_LEVEL_LIST));
            }
        }

        var query = String.format("MATCH (p:People)-[r:KNOWS]->(s:Skill) WHERE ALL(pair IN [%s] " +
                                  " WHERE (p)-[r]->(s {code: pair.skillcode}) " +
                                  " AND ANY (lvl IN pair.knowslevel WHERE (p)-[r {level: lvl}]->(s {code: pair.skillcode}) AND r.experience >= pair.experience " +
                                  " AND p.assignable = TRUE)) " +
                                  " RETURN DISTINCT p.code", String.join(",", filter));

        var peopleCodes = client.query(query).fetchAs(String.class).all();

        return peopleCodes
                .parallelStream()
                .map(code -> crud.findByCode(code, PeopleView.class))
                .flatMap(Optional::stream)
                .map(mapper::fromView)
                .toList();

    }

    private String fillFilterBuilder(final PeopleSkill peopleSkill, final List<String> levelList) {
        return String.format("{skillcode:'%s', knowslevel:[%s], experience:%d}",
                peopleSkill.skillCode(), String.join(",", levelList), peopleSkill.minExp());
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
                .flatMap(Optional::stream)
                .map(mapper::fromView)
                .toList();
    }


}
