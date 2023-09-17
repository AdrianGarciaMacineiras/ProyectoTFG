package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiFunction;

import static com.sngular.skilltree.model.EnumLevelReq.MANDATORY;
import static com.sngular.skilltree.model.EnumStatus.QUALIFICATION;

import com.sngular.skilltree.common.exceptions.AssignUnableException;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.CandidateCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PositionCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.CandidateNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PositionNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.EnumStatus;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.PositionSkill;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CandidateRepositoryImpl implements CandidateRepository {

    public static final String ADVANCED = "'advanced'";

    private static final List<String> LOW_LEVEL_LIST = List.of("'low'", "'middle'", ADVANCED);

    private static final List<String> MID_LEVEL_LIST = List.of("'middle'", ADVANCED);

    private static final List<String> HIGH_LEVEL_LIST = List.of(ADVANCED);

    private static final List<String> CANDIDATE_OPEN_STATUS = List.of("'KO'", "'OK'", "'UNKNOWN'");

    public static final String NULL = "null";

    public static final String BIRTH_DATE = "birthDate";

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
            candidateBuilder.position(positionNodeMapper.fromNode(positionCrudRepository.getByCode(positionNode.getCode())));
        }

        for (var candidateRelationship : candidateRelationshipList){
            candidateBuilder.code(candidateRelationship.code());
            candidateBuilder.id(candidateRelationship.id());
            candidateBuilder.candidate(peopleNodeMapper.fromNode(candidateRelationship.candidate()));
            candidateBuilder.resolutionDate(candidateRelationship.resolutionDate());
            candidateBuilder.introductionDate(candidateRelationship.resolutionDate());
            candidateBuilder.status(EnumStatus.valueOf(candidateRelationship.status().name()));
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
    public Candidate findByCode(String candidateCode) {
        var candidate = crud.findByCode(candidateCode);
        if(Objects.isNull(candidate))
            return null;
        else
            return mapper.fromNode(candidate);
    }

    @Override
    public boolean deleteByCode(String candidateCode) {
        var node = crud.findByCode(candidateCode);
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
                                              .mappedBy((TypeSystem t, Record result) -> getCandidateBuilder(result).build())
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
                    case LOW -> filter.add(fillFilterBuilder(positionSkill.skill().getCode(), LOW_LEVEL_LIST, positionSkill.minExp()));
                    case MEDIUM -> filter.add(fillFilterBuilder(positionSkill.skill().getCode(), MID_LEVEL_LIST, positionSkill.minExp()));
                    default -> filter.add(fillFilterBuilder(positionSkill.skill().getCode(), HIGH_LEVEL_LIST, positionSkill.minExp()));
                }
            }
        }

        var query = String.format("MATCH (p:People)-[r:KNOWS]->(s:Skill) WHERE ALL(pair IN [%s] " +
                                  " WHERE (p)-[r]->(s {code: pair.skillcode}) " +
                                  " AND ANY (lvl IN pair.knowslevel WHERE (p)-[r {level: lvl}]->(s {code: pair.skillcode}) AND r.experience >= pair.experience" +
                                  " AND p.assignable = TRUE)) " +
                                  " RETURN DISTINCT p", String.join(",", filter));

        var peopleList = client.query(query).fetchAs(People.class)
                .mappedBy(getTypeSystemRecordPeopleBiFunction())
                .all();

        Map<String, People> knowsMap = new HashMap<>();

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
            var candidateQuery = String.format("MATCH(p:People{code:'%s'}),(n:Position{code:'%s'})" +
                    "CREATE (n)-[r:CANDIDATE{code:'%s',status:'%s',creationDate:date('%s')}]->(p)" +
                    "RETURN p,r.code,r.status,r.creationDate,n.code,ID(r)", people.code(), positionCode, people.code() + "-" + people.employeeId(), QUALIFICATION, LocalDate.now());

            var candidate = client.query(candidateQuery).fetchAs(Candidate.class)
                                  .mappedBy((TypeSystem t, Record result) -> getCandidateBuilder(result).build())
                                  .one();

            candidate.ifPresent(candidateList::add);
        }

        return candidateList;
    }

    @Override
    public Optional<Candidate> findByPeopleAndPosition(String positionCode, String peopleCode) {

        var query = String.format("MATCH(p:People{code:%s})-[r:CANDIDATE]-(n:Position{code:'%s'}) " +
                        "WHERE NOT(r.status NOT IN %s) RETURN p,r.code, r.introductionDate, r.resolutionDate, r.creationDate, r.status, ID(r), n",
                peopleCode, positionCode, CANDIDATE_OPEN_STATUS);

        return client
                .query(query)
                .fetchAs(Candidate.class)
                .mappedBy((TypeSystem t, Record result) -> getCandidateBuilder(result).build())
                .one();
    }

    @Override
    public void assignCandidate(String positionCode, String peopleCode, Candidate candidate) {

        var position = positionCrudRepository.getByCode(positionCode);

        if (Objects.isNull(candidate)) {
            throw new AssignUnableException("Candidate");
        }

        var query = String.format("MATCH(p:People),(s:Position)" +
                        "WHERE p.code=%s AND s.code='%s'" +
                        "CREATE(s)-[r:COVER{assignDate:date('%s'), role:'%s', dedication:100}]->(p)",
                peopleCode, positionCode, LocalDate.now(), position.getRole());
        client.query(query).run();

        var queryStatusOK = String.format("MATCH(p:People)-[r:CANDIDATE]-(s:Position) " +
                        "WHERE ID(r) = %s SET r.status = 'OK'"
                , candidate.id());
        client.query(queryStatusOK).run();

        var queryStatusKO = String.format("MATCH(p:People)-[r:CANDIDATE]-(n:Position{code:'%s'}) " +
                "WHERE NOT r.status = 'KO' AND NOT r.status = 'OK' SET r.status = 'KO'"
                , positionCode);
        client.query(queryStatusKO).run();

        var queryAssignableTrue = String.format("MATCH(p:People{code:%s}) SET p.assignable = FALSE"
                , peopleCode);
        client.query(queryAssignableTrue).run();
    }

    @Override
    public List<Candidate> getCandidatesByPosition(String positionCode) {
        var query = String.format("MATCH(n:Position{code:'%s'})-[r:CANDIDATE]-(p:People) " +
                        "RETURN p, r.code, r.introductionDate, r.resolutionDate, r.creationDate, r.status, ID(r), n.code",
                positionCode);

        return new ArrayList<>(client
                .query(query)
                .fetchAs(Candidate.class)
                .mappedBy((TypeSystem t, Record result) -> getCandidateBuilder(result).build())
                .all());
    }

    @Override
    public List<Candidate> getCandidatesByPeople(String peopleCode) {
        var query = String.format("MATCH(n:Position)-[r:CANDIDATE]-(p:People{code:%s}) " +
                        "RETURN p, r.code, r.introductionDate, r.resolutionDate, r.creationDate, r.status, ID(r), n.code",
                peopleCode);

        return new ArrayList<>(client
                .query(query)
                .fetchAs(Candidate.class)
                .mappedBy((TypeSystem t, Record result) -> getCandidateBuilder(result).build())
                .all());
    }

    private Candidate.CandidateBuilder getCandidateBuilder(Record result) {

        var candidateBuilder = Candidate.builder();
        candidateBuilder.id(result.get("ID(r)").toString());
        candidateBuilder.code(result.get("r.code").asString());
        candidateBuilder.status(EnumStatus.valueOf(result.get("r.status").asString()));
        candidateBuilder.introductionDate(NULL.equalsIgnoreCase(result.get("r.introductionDate").asString()) ? null : result.get("r.introductionDate").asLocalDate());
        candidateBuilder.resolutionDate(NULL.equalsIgnoreCase(result.get("r.resolutionDate").asString()) ? null : result.get("r.resolutionDate").asLocalDate());
        candidateBuilder.creationDate(result.get("r.creationDate").asLocalDate());

        People peopleBuilder = getPeople(result);

        candidateBuilder.candidate(peopleBuilder);
        candidateBuilder.position(positionNodeMapper.fromNode(positionCrudRepository.findPosition(result.get("n.code").asString())));
        return candidateBuilder;
    }

    private static People getPeople(Record result) {
        var people = result.get("p");
        return People.builder()
                     .name(people.get("name").asString())
                     .surname(people.get("surname").asString())
                     .employeeId(people.get("employeeId").asString())
                     //.birthDate(NULL.equalsIgnoreCase(result.get(BIRTH_DATE).asString()) ? null : result.get(BIRTH_DATE).asLocalDate())
                     .code(people.get("code").asString())
                     .deleted(people.get("deleted").asBoolean())
                     .build();
    }

    private String fillFilterBuilder(final String skillCode, final List<String> levelList, final Integer minExp) {
        return String.format("{skillcode:'%s', knowslevel:[%s], experience:%d}", skillCode, String.join(",", levelList), minExp);
    }

    private static BiFunction<TypeSystem, Record, People> getTypeSystemRecordPeopleBiFunction() {
        return (TypeSystem t, Record result) -> {
            var people = result.get("p");
            return People.builder()
                         .name(people.get("name").asString())
                         .surname(people.get("surname").asString())
                         .employeeId(people.get("employeeId").asString())
                         //.birthDate(NULL.equalsIgnoreCase(result.get(BIRTH_DATE).asString()) ? null : result.get(BIRTH_DATE).asLocalDate())
                         .code(people.get("code").asString())
                         .deleted(people.get("deleted").asBoolean())
                         .build();
        };
    }

}
