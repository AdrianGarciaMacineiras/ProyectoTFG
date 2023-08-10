package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.infraestructura.SkillRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.SkillCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.SkillNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SkillNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.SubSkillsRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.tool.NodeUtil;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Skill;
import com.sngular.skilltree.model.StrategicTeamSkill;
import com.sngular.skilltree.model.StrategicTeamSkillNotUsed;
import com.sngular.skilltree.model.views.SkillStatsTittle;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.internal.InternalNode;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = "skills")
    public List<Skill> findAll() {
        var parentNodeCodes = crud.findAllParents();
        List<Skill> subSkills;
        List<Skill> skills = new ArrayList<>();
        List<SkillNode> skillNodeList = new ArrayList<>();
        for (String parentNode : parentNodeCodes) {
            skillNodeList.add(crud.findByCode(parentNode));
        }
        for (SkillNode skillNode : skillNodeList) {
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
    @Cacheable(cacheNames = "skills")
    public Skill findByCode(String skillcode) {
        var skillNode = crud.findByCode(skillcode);
        List<Skill> subSkills = new ArrayList<>();
        for (SubSkillsRelationship subSkillNode : skillNode.getSubSkills()) {
            var toSkill = mapper.fromNode(subSkillNode.skillNode());
            subSkills.add(toSkill);
        }
        return new Skill(skillcode, skillNode.getName(), subSkills);
    }

    @Override
    @Cacheable(cacheNames = "skills")
    public Skill findSkill(String skillcode) {
        var skillNode = crud.findByCode(skillcode);
        return new Skill(skillcode, skillNode.getName(), new ArrayList<>());
    }

    @Override
    @Cacheable(cacheNames = "skills")
    public Skill findByName(String skillname) {
        var skillNode = crud.findByName(skillname);
        return new Skill(skillNode.getCode(), skillname, new ArrayList<>());
    }

    @Override
    public List<StrategicTeamSkill> getStrategicSkillsUse() {

        var query = "MATCH (t:Team)-[k:STRATEGIC]-(s:Skill)-[r:WORK_WITH]-(p:People)--(t)\n " +
                    "RETURN t.name as teamName, collect(s.name) as skillList, count(s), collect(p) as teamMembers";

        var result = new ArrayList<>(client
                .query(query)
                .fetchAs(StrategicTeamSkill.class)
                .mappedBy((TypeSystem t, Record queryResult) -> {

                    var peopleList = getPeople(queryResult.get("teamMembers", Collections.emptyList()));

                    return StrategicTeamSkill.builder()
                            .teamName(queryResult.get("teamName").asString())
                            .peopleList(peopleList)
                            .skillList(toSkillList(queryResult.get("skillList", Collections.emptyList())))
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
                .mappedBy((TypeSystem t, Record queryResult) -> StrategicTeamSkillNotUsed.builder()
                        .teamName(queryResult.get("t.name").asString())
                        .skillList(List.of(queryResult.get("s.name").asString()))
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

    private static List<People> getPeople(List<Object> peopleList) {
        var result = new HashMap<String, People>();
        peopleList.forEach(peopleObject -> {
            var peopleNode = ((InternalNode) peopleObject);
            var people = People.builder()
                    .name(peopleNode.get("name").asString())
                    .surname(peopleNode.get("surname").asString())
                    .employeeId(peopleNode.get("employeeId").asString())
                    .birthDate(NodeUtil.getValueAsLocalDate(peopleNode, "birthdate"))
                    .code(peopleNode.get("code").asString())
                    .deleted(false)
                    .build();
            result.putIfAbsent(people.code(), people);
        });
        return new ArrayList<>(result.values());
    }

    private List<String> toSkillList(List<Object> skillNodeList) {
        var skillList = new HashSet<String>();
        skillNodeList.forEach(skill -> skillList.add(skill.toString()));
        return new ArrayList<>(skillList);
    }

    @Override
    public SkillStatsTittle getSkillStatsByTittle(String tittle) {

        final var query = """
                match (s:Skill)-[r:KNOWS]-(p:People)
                 where p.title = $tittle
                 return s.namespace as skill, count(r) as total
                """;
        final var rootSkill = SkillStatsTittle.builder().name("Skills").build();
        client
                .query(query)
                .bindAll(Map.of("tittle", tittle))
                .fetchAs(SkillStatsTittle.class)
                .mappedBy((TypeSystem t, Record queryResult) ->
                        SkillStatsTittle
                                .builder()
                                .name(queryResult.get("skill").asString().substring(queryResult.get("skill").asString().lastIndexOf('.') + 1))
                                .total(queryResult.get("total", 0))
                                .parent(queryResult.get("skill").asString().substring(0, queryResult.get("skill").asString().lastIndexOf('.')))
                                .build())
                .all()
                .forEach(skillStat -> rootSkill.addStat(skillStat.parent().substring(skillStat.parent().indexOf(".") + 1), skillStat)
                );

        return rootSkill;
    }
}
