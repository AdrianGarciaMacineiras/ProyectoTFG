package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.common.exceptions.AssignUnableException;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.CandidateCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PositionCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.CandidateNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PositionNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.model.*;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.types.TypeSystem;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Repository;
import org.springframework.data.neo4j.core.Neo4jClient;


import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;

import static com.sngular.skilltree.model.EnumLevelReq.MANDATORY;
import static com.sngular.skilltree.model.EnumStatus.*;


@Repository
@RequiredArgsConstructor
public class CandidateRepositoryImpl implements CandidateRepository {

    private final List<String> LOW_LEVEL_LIST = List.of("'LOW'", "'MEDIUM'", "'HIGH'");
    private final List<String> MID_LEVEL_LIST = List.of( "'MEDIUM'", "'HIGH'");
    private final List<String> HIGH_LEVEL_LIST = List.of("'HIGH'");
    private final List<String> CANDIDATE_OPEN_STATUS = List.of("'OPENED'", "'ASSIGNED'", "'INTERVIEWED'");

    private final CandidateCrudRepository crud;

    private final PositionCrudRepository positionCrudRepository;

    private final PositionNodeMapper positionNodeMapper;

    private final PeopleNodeMapper peopleNodeMapper;

    private final CandidateNodeMapper mapper;

    private final Neo4jClient client;

    @Override
    public List<Candidate> findAll() {
        var positionNodeList = positionCrudRepository.findAll();
        List<CandidateRelationship> candidateRelationshipList = new ArrayList<>();
        Candidate.CandidateBuilder candidateBuilder = Candidate.builder();

        for (var positionNode : positionNodeList){
            candidateRelationshipList.addAll(positionNode.getCandidates());
            candidateBuilder.position(positionNodeMapper.fromNode(positionCrudRepository.findByCode(positionNode.getCode())));
        }

        for (var candidateRelationship : candidateRelationshipList){
            candidateBuilder.code(candidateRelationship.code());
            candidateBuilder.id(candidateRelationship.id());
            candidateBuilder.candidate(peopleNodeMapper.fromNode(candidateRelationship.candidate()));
            candidateBuilder.resolutionDate(candidateRelationship.resolutionDate());
            candidateBuilder.introductionDate(candidateRelationship.resolutionDate());
            candidateBuilder.status(candidateRelationship.status());
            candidateBuilder.skills(peopleNodeMapper.knowsRelationshipListToKnowsList(candidateRelationship.candidate().getKnows()));
            candidateBuilder.build();
        }

        return mapper.map(candidateRelationshipList);
    }

    @Override
    public Candidate save(Candidate candidate) {
        return mapper.fromNode(crud.save(mapper.toNode(candidate)));
    }

    @Override
    public Candidate findByCode(String candidatecode) {
        return mapper.fromNode(crud.findByCode(candidatecode));
    }

    @Override
    public boolean deleteByCode(String candidatecode) {
        var node = crud.findByCode(candidatecode);
        crud.save(node);
        crud.delete(node);
        return true;
    }

    @Override
    public List<Candidate> findAllCandidates() {

        Map<String, Candidate> knowsMap = new HashMap<>();

        var candidateList = new ArrayList<>(client
                .query("MATCH (o:Skill)-[m:NEEDS]-(n:Position)-[r:CANDIDATE]-(p)-[s:KNOWS]-(k:Skill) " +
                        "WHERE o.code=k.code RETURN n.code, r.code, r.status, r.creationDate, r.introductionDate, r.resolutionDate, p,s,k.name")
                .fetchAs(Candidate.class)
                .mappedBy((TypeSystem t, Record record) -> {

                    Candidate.CandidateBuilder candidateBuilder = getCandidateBuilder(record);

                    return candidateBuilder.build();
                })
                .all());

        candidateList.forEach(candidate ->
            knowsMap.compute(candidate.code(), (code, aggCandidate) -> {
                if(Objects.isNull(aggCandidate)){
                    return candidate;
                } else {
                    var knowList = new ArrayList<>(aggCandidate.skills());
                    knowList.addAll(candidate.skills());
                    aggCandidate = aggCandidate.toBuilder().skills(knowList).build();
                }
                return aggCandidate;
            })
        );
        return new ArrayList<>(knowsMap.values());
    }

    @Override
    public List<Candidate> generateCandidates(String positionCode, List<PositionSkill> positionSkills) {

        var queryStatusKO = String.format("MATCH(p:People)-[r:CANDIDATE]-(n:Position{code:'%s'}) " +
                "WHERE NOT r.status = 'KO' AND NOT r.status = 'OK' SET r.status = 'KO'",positionCode);
        client.query(queryStatusKO).run();

        final var filter = new ArrayList<String>();
        for (var positionSkill : positionSkills){
            if (MANDATORY.equals(positionSkill.levelReq())) {
                switch (positionSkill.minLevel()) {
                    case LOW -> filter.add(fillFilterBuilder(positionSkill.skill().code(), LOW_LEVEL_LIST));
                    case MEDIUM -> filter.add(fillFilterBuilder(positionSkill.skill().code(), MID_LEVEL_LIST));
                    default -> filter.add(fillFilterBuilder(positionSkill.skill().code(), HIGH_LEVEL_LIST));
                }
            }
        }

        var query = String.format("MATCH (p:People)-[r:KNOWS]->(s:Skill) WHERE ALL(pair IN [%s] " +
                " WHERE (p)-[:KNOWS]->(:Skill {code: pair.skillcode}) AND ANY(lvl IN pair.knowslevel WHERE (p)-[:KNOWS {level: lvl}]->(:Skill {code: pair" +
                ".skillcode})))" +
                " RETURN DISTINCT p", String.join(",", filter));

        var peopleList = client.query(query).fetchAs(People.class)
                .mappedBy(getTypeSystemRecordPeopleBiFunction())
                .all();

        Map<Long, People> knowsMap = new HashMap<>();

        peopleList.forEach(people ->
                knowsMap.compute(people.code(), (code, aggPeople) -> {
                    if(Objects.isNull(aggPeople)){
                        return people;
                    } else {
                        var knowList = new ArrayList<>(aggPeople.knows());
                        knowList.addAll(people.knows());
                        aggPeople = aggPeople.toBuilder().knows(knowList).build();
                    }
                    return aggPeople;
                })
        );

        var candidateList = new ArrayList<Candidate>();

        for (var people: peopleList) {
            var candidateQuery = String.format("MATCH(p:People{code:%d}),(n:Position{code:'%s'})" +
                    "CREATE (n)-[r:CANDIDATE{code:'%s',status:'%s',creationDate:date('%s')}]->(p)" +
                    "RETURN p,r,n", people.code(), positionCode,people.code()+ "-" + people.employeeId(),EnumStatus.OPENED,LocalDate.now().toString());

            var candidate = client.query(candidateQuery).fetchAs(Candidate.class)
                    .mappedBy((TypeSystem t, Record record) ->{

                        Candidate.CandidateBuilder candidateBuilder = getCandidateBuilder(record);

                        return candidateBuilder.build();
                    })
                    .one();

            candidateList.add(candidate.get());
        }

        return candidateList;
    }

    @Override
    public List<Candidate> findByPeopleandPosition(String positionCode, Long peopleCode) {

        var query = String.format("MATCH(p:People{code:%d})-[r:CANDIDATE]-(n:Position{code:'%s'}) " +
                "WHERE r.status IN %s RETURN p,r.code, r.introductionDate, r.resolutionDate, r.creationDate, r.status, ID(r), n",
                peopleCode, positionCode, CANDIDATE_OPEN_STATUS);

        var candidateList = new ArrayList<>(client
                .query(query)
                .fetchAs(Candidate.class)
                .mappedBy((TypeSystem t, Record record)->{

                    Candidate.CandidateBuilder candidateBuilder = getCandidateBuilder(record);

                    return candidateBuilder.build();
                })
                .all()
        );

        return candidateList;
    }

    @Override
    public void assignCandidate(String positionCode, Long peopleCode, List<Candidate> candidates) {

        var position = positionCrudRepository.findByCode(positionCode);

        Candidate openCandidate = null;
        for (var candidate: candidates){
            if(candidate.status() == OPENED || candidate.status() == INTERVIEWED || candidate.status() == ASSIGNED){
                openCandidate = candidate;
                break;
            }
        }
        if (Objects.isNull(openCandidate)){
           throw new AssignUnableException("Candidate");
        }

       var query = String.format("MATCH(p:People),(s:Position)" +
                       "WHERE p.code=%d AND s.code='%s'" +
                       "CREATE(s)-[r:ASSIGN{assignDate:date('%s'), role:'%s', dedication:'100/100'}]->(p)"
               , peopleCode, positionCode, LocalDate.now().toString(), position.getRole());
        client.query(query).run();

        var queryStatusOK = String.format("MATCH(p:People)-[r:CANDIDATE]-(s:Position) WHERE ID(r) = %d SET r.status = 'OK'"
                ,openCandidate.id());
        client.query(queryStatusOK).run();

        var queryStatusKO = String.format("MATCH(p:People)-[r:CANDIDATE]-(n:Position{code:'%s'}) " +
                "WHERE NOT r.status = 'KO' AND NOT r.status = 'OK' SET r.status = 'KO'",positionCode);
        client.query(queryStatusKO).run();

    }

    @Override
    public List<Candidate> getCandidates(String positionCode) {
        var query = String.format("MATCH(n:Position{code:'%s'})-[r:CANDIDATE]-(p:People) " +
                        "RETURN p, r.code, r.introductionDate, r.resolutionDate, r.creationDate, r.status, ID(r), n.code",
                positionCode);

        return new ArrayList<>(client
                .query(query)
                .fetchAs(Candidate.class)
                .mappedBy((TypeSystem t, Record record)->{

                    Candidate.CandidateBuilder candidateBuilder = getCandidateBuilder(record);

                    return candidateBuilder.build();
                })
                .all());
    }

    @Override
    public List<Candidate> getCandidates(Long peopleCode) {
        var query = String.format("MATCH(n:Position)-[r:CANDIDATE]-(p:People{code:%d}) " +
                        "RETURN p, r.code, r.introductionDate, r.resolutionDate, r.creationDate, r.status, ID(r), n.code",
                peopleCode);

        return new ArrayList<>(client
                .query(query)
                .fetchAs(Candidate.class)
                .mappedBy((TypeSystem t, Record record)->{

                    Candidate.CandidateBuilder candidateBuilder = getCandidateBuilder(record);

                    return candidateBuilder.build();
                })
                .all());
    }

    private Candidate.CandidateBuilder getCandidateBuilder(Record record) {

        var candidateBuilder = Candidate.builder();
        candidateBuilder.id(record.get("ID(r)").asLong());
        candidateBuilder.code(record.get("r.code").asString());
        candidateBuilder.status(EnumStatus.valueOf(record.get("r.status").asString()));
        candidateBuilder.introductionDate((record.get("r.introductionDate").asString() == "null") ? null : record.get("r.introductionDate").asLocalDate());
        candidateBuilder.resolutionDate((record.get("r.resolutionDate").asString() == "null") ? null : record.get("r.resolutionDate").asLocalDate());
        candidateBuilder.creationDate(record.get("r.creationDate").asLocalDate());

        People peopleBuilder = getPeople(record);

        candidateBuilder.candidate(peopleBuilder);
        candidateBuilder.position(positionNodeMapper.fromNode(positionCrudRepository.findPosition(record.get("n.code").asString())));
        return candidateBuilder;
    }

    private static People getPeople(Record record) {
        return People.builder()
                .name(record.get("p").get("name").asString())
                .surname(record.get("p").get("surname").asString())
                .employeeId(record.get("p").get("employeeId").asString())
                .birthDate(record.get("p").get("birthDate").asLocalDate())
                .code(record.get("p").get("code").asLong())
                .deleted(record.get("p").get("deleted").asBoolean())
                .build();
    }

    private String fillFilterBuilder(final String skillCode, final List<String> levelList) {
        return String.format("{skillcode:'%s', knowslevel:[%s]}", skillCode, String.join(",", levelList));
    }

    private static BiFunction<TypeSystem, Record, People> getTypeSystemRecordPeopleBiFunction() {
        return (TypeSystem t, Record record) ->

                People.builder()
                        .name(record.get("p").get("name").asString())
                        .surname(record.get("p").get("surname").asString())
                        .employeeId(record.get("p").get("employeeId").asString())
                        .birthDate(record.get("p").get("birthDate").asLocalDate())
                        .code(record.get("p").get("code").asLong())
                        .deleted(record.get("p").get("deleted").asBoolean())
                        .build();
    }

}
