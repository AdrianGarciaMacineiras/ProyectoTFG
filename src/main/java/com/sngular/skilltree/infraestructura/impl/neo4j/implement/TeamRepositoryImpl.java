package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.TeamCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.TeamNodeMapper;
import com.sngular.skilltree.model.*;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

    private final TeamCrudRepository crud;

    private final TeamNodeMapper mapper;

    private final PeopleCrudRepository peopleCrudRepository;

    private final Neo4jClient client;

    public static final String NULL = "null";

    @Override
    public List<Team> findAll() { return mapper.map(crud.findByDeletedIsFalse()); }

    @Override
    public Team save(Team team) {
        var teamNode = mapper.toNode(team);
        for (var member : teamNode.getMembers()){
            var peopleNode = peopleCrudRepository.findByCode(member.peopleNode().getCode());
            if (Objects.isNull(peopleNode) || peopleNode.isDeleted()) {
                throw new EntityNotFoundException("People", peopleNode.getCode());
            }
        }
        return mapper.fromNode(crud.save(teamNode));
    }

    @Override
    public Team findByCode(String teamcode) { return mapper.fromNode(crud.findByCode(teamcode)); }

    @Override
    public List<Member> getMembers(String teamcode) {

        var query = String.format("MATCH(t:Team{code:'%s'})-[r:MEMBER_OF]-(p:People) RETURN p,r",teamcode);

        return new ArrayList<>(client.query(query)
                .fetchAs(Member.class)
                .mappedBy((TypeSystem t, Record record) ->{

                    People person = getPeople(record);

                    var member = record.get("r");

                    return Member.builder()
                            .id(member.get("id").asString())
                            .charge(NULL.equalsIgnoreCase(member.get("charge").asString()) ? null : EnumCharge.valueOf(member.get("charge").asString()))
                            .people(person)
                            .build();

                })
                .all());
    }

    @Override
    public boolean deleteByCode(String teamcode) {
        var node = crud.findByCode(teamcode);
        node.setDeleted(true);
        crud.save(node);
        return true;
    }

    @Override
    public List<Team> findByDeletedIsFalse() {
        return mapper.map(crud.findByDeletedIsFalse());
    }

    private static People getPeople(Record result) {
        var people = result.get("p");
        return People.builder()
                .name(people.get("name").asString())
                .surname(people.get("surname").asString())
                .employeeId(people.get("employeeId").asString())
                .birthDate(people.get("birthDate").asLocalDate())
                .code(people.get("code").asLong())
                .deleted(people.get("deleted").asBoolean())
                .build();
    }
}
