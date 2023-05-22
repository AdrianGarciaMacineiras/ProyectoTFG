package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.TeamRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.TeamCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.TeamNodeMapper;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.StrategicTeamSkill;
import com.sngular.skilltree.model.StrategicUse;
import com.sngular.skilltree.model.Team;
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

    private final PeopleNodeMapper peopleNodeMapper;

    private final Neo4jClient client;

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
    public List<People> getMembers(String teamcode) {
        var codeList = crud.findMembersByTeamCode(teamcode);
        return codeList
                .parallelStream()
                .map(peopleCrudRepository::findByCode)
                .map(peopleNodeMapper::fromNode)
                .toList();
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

    @Override
    public List<StrategicTeamSkill> getStrategicSkillsUse() {

        var query = "MATCH (t:Team)-[k:STRATEGIC]-(s:Skill)-[r:WORK_WITH]-(p:People)--(t)\n " +
                "RETURN t.name, collect(s.name), count(s), p, s.name";

        var result = new ArrayList<>(client
                .query(query)
                .fetchAs(StrategicTeamSkill.class)
                .mappedBy((TypeSystem t, Record record) -> {

                    People peopleBuilder = getPeople(record);

                    var strategicUseBuilder = StrategicUse.builder()
                            .skillName(record.get("s.name").asString())
                            .peopleList(List.of(peopleBuilder))
                            .build();

                    var strategicTeamSkillBuilder = StrategicTeamSkill.builder()
                            .teamName(record.get("t.name").asString())
                            .skillList(List.of(strategicUseBuilder))
                            .build();

                    return strategicTeamSkillBuilder;
                })
                .all());


        Map<String, StrategicTeamSkill> strategicTeamSkillMap = new HashMap<>();

        Map<String, StrategicUse> strategicUseMap = new HashMap<>();

        result.forEach(team ->
                strategicTeamSkillMap.compute(team.teamName(), (name, aggStrategicSkill) -> {
                    if(Objects.isNull(aggStrategicSkill)){
                        return team;
                    } else {
                        var skillList = new ArrayList<>(aggStrategicSkill.skillList());
                        skillList.addAll(team.skillList());
                        aggStrategicSkill = aggStrategicSkill.toBuilder().skillList(skillList).build();

                    }
                    return aggStrategicSkill;
                })
        );


/*        aggStrategicSkill.skillList().forEach(skill ->
                strategicUseMap.compute(skill.skillName(), (skillName, aggPeople) -> {
                    if(Objects.isNull(aggPeople)){
                        return skill;
                    } else {
                        var peopleList = new ArrayList<>(aggPeople.peopleList());
                        peopleList.addAll(skill.peopleList());
                        aggPeople = aggPeople.toBuilder().peopleList(peopleList).build();
                    }
                    return aggPeople;
                }));*/



        return new ArrayList<>(strategicTeamSkillMap.values());
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
