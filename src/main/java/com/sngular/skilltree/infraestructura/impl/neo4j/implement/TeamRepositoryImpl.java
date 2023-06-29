package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.TeamCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.TeamNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.TeamView;
import com.sngular.skilltree.model.Member;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Team;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class TeamRepositoryImpl implements TeamRepository {

    private final TeamCrudRepository crud;

    private final TeamNodeMapper mapper;

    private final PeopleCrudRepository peopleCrudRepository;

    private final Neo4jClient client;

    public static final String NULL = "null";

    @Override
    public List<Team> findAll() {
        return crud.findByDeletedIsFalse(TeamView.class).stream().map(mapper::map).toList();
    }

    @Override
    public Team save(Team team) {
        var teamNode = mapper.toNode(team);
        for (var member : teamNode.getMembers()){
            var peopleNode = peopleCrudRepository.findByCode(member.people().getCode(), PeopleNode.class);
            if (Objects.isNull(peopleNode) || peopleNode.isDeleted()) {
                throw new EntityNotFoundException("People", peopleNode.getCode());
            }
        }
        return mapper.fromNode(crud.save(teamNode));
    }

    @Override
    public Team findByCode(String teamcode) { return mapper.map(crud.findByCodeAndDeletedIsFalse(teamcode,
            TeamView.class)); }

    @Override
    public List<Member> getMembers(String teamcode) {

        var query = String.format("MATCH(t:Team{code:'%s'})-[r:MEMBER_OF]-(p:People) RETURN p,r",teamcode);

        return new ArrayList<>(client.query(query)
                .fetchAs(Member.class)
                .mappedBy((TypeSystem t, Record queryResult) -> {

                    People person = getPeople(queryResult);

                    var member = queryResult.get("r");

                    return Member.builder()
                            .id(member.get("id").asString())
                            .charge(NULL.equalsIgnoreCase(member.get("charge").asString()) ? null : member.get("charge").asString())
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
        return crud.findByDeletedIsFalse(TeamView.class).stream().map(mapper::map).toList();
    }

    private static People getPeople(Record result) {
        var people = result.get("p");
        return People.builder()
                .name(people.get("name").asString())
                .surname(people.get("surname").asString())
                .employeeId(people.get("employeeId").asString())
                .birthDate(people.get("birthDate").asLocalDate())
                .code(people.get("code").asString())
                .deleted(people.get("deleted").asBoolean())
                .build();
    }
}
