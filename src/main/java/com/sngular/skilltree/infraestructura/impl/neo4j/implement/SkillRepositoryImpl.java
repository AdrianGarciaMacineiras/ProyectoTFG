package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.infraestructura.impl.neo4j.SkillCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SubSkillsRelationship;
import com.sngular.skilltree.model.*;
import com.sngular.skilltree.infraestructura.SkillRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.SkillNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class SkillRepositoryImpl implements SkillRepository {

    private final SkillCrudRepository crud;

    private final SkillNodeMapper mapper;

    private final Neo4jClient client;

    @Override
    public List<Skill> findAll() {
        var skillNodeList = crud.findAll();
        List<Skill> subSkills;
        List<Skill> skills = new ArrayList<>();
        for(SkillNode skillNode: skillNodeList){
            subSkills = new ArrayList<>();
            for (SubSkillsRelationship subSkillNode : skillNode.getSubSkills()) {
                var toSkill = mapper.fromNode(subSkillNode.skillNode());
                subSkills.add(toSkill);
            }
            Skill skill = new Skill(skillNode.getCode(), skillNode.getName(), subSkills);
            skills.add(skill);
        }
        return skills;
    }

    @Override
    public Skill findByCode(String skillcode) {
        var skillNode = crud.findByCode(skillcode);
        List<Skill> subSkills = new ArrayList<>();
        for (SubSkillsRelationship subSkillNode : skillNode.getSubSkills()) {
            var toSkill = mapper.fromNode(subSkillNode.skillNode());
            subSkills.add(toSkill);
        }
        Skill skill = new Skill(skillcode, skillNode.getName(), subSkills);
        return skill;
    }

    @Override
    public Skill findSkill(String skillcode) {
        var skillNode = crud.findByCode(skillcode);
        Skill skill = new Skill(skillcode, skillNode.getName(), new ArrayList<>());
        return skill;
    }

    @Override
    public Skill findByName(String skillname) {
        var skillNode = crud.findByName(skillname);
        Skill skill = new Skill(skillNode.getCode(), skillname, new ArrayList<>());
        return skill;
    }

    @Override
    public List<StrategicTeamSkill> getStrategicSkillsUse() {

        var query = "MATCH (t:Team)-[k:STRATEGIC]-(s:Skill)-[r:WORK_WITH]-(p:People)--(t)\n " +
                "RETURN t.name, collect(s.name), count(s), p, s.name";

        var result = new ArrayList<>(client
                .query(query)
                .fetchAs(StrategicTeamSkill.class)
                .mappedBy((TypeSystem t, Record record) -> {

                    People people = getPeople(record);

                    return StrategicTeamSkill.builder()
                            .teamName(record.get("t.name").asString())
                            .peopleList(List.of(people))
                            .build();

                })
                .all());


        Map<String, StrategicTeamSkill> strategicTeamSkillMap = new HashMap<>();

        result.forEach(team ->
                strategicTeamSkillMap.compute(team.teamName(), (name, aggStrategicSkill) -> {
                    if(Objects.isNull(aggStrategicSkill)){
                        return team;
                    } else {
                        var peopleList = new ArrayList<>(aggStrategicSkill.peopleList());
                        peopleList.addAll(team.peopleList());
                        aggStrategicSkill = aggStrategicSkill.toBuilder().peopleList(peopleList).build();

                    }
                    return aggStrategicSkill;
                })
        );

        return new ArrayList<>(strategicTeamSkillMap.values());
    }

    @Override
    public List<StrategicTeamSkillNotUsed> getNoStrategicSkillsUse() {

        var query = "MATCH (t:Team)-[k:STRATEGIC]-(s:Skill) " +
                "WHERE NOT ((s)-[:WORK_WITH]-(:People)--(t))" +
                " RETURN t.name, collect(s.name), count(s), s.name";

        var result = new ArrayList<>(client
                .query(query)
                .fetchAs(StrategicTeamSkillNotUsed.class)
                .mappedBy((TypeSystem t, Record record) -> StrategicTeamSkillNotUsed.builder()
                        .teamName(record.get("t.name").asString())
                        .skillList(List.of(record.get("s.name").asString()))
                        .build())
                .all());


        Map<String, StrategicTeamSkillNotUsed> strategicTeamSkillNotUsedMap = new HashMap<>();

        result.forEach(team ->
                strategicTeamSkillNotUsedMap.compute(team.teamName(), (name, aggStrategicSkill) -> {
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


        return new ArrayList<>(strategicTeamSkillNotUsedMap.values());
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
