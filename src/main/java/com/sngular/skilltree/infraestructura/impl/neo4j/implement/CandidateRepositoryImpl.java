package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import static com.sngular.skilltree.model.EnumLevelReq.MANDATORY;
import static com.sngular.skilltree.model.EnumStatus.ASSIGNED;
import static com.sngular.skilltree.model.EnumStatus.INTERVIEWED;
import static com.sngular.skilltree.model.EnumStatus.OPENED;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

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

    public static final String HIGH = "'HIGH'";

    private static final List<String> LOW_LEVEL_LIST = List.of("'LOW'", "'MEDIUM'", HIGH);

    private static final List<String> MID_LEVEL_LIST = List.of("'MEDIUM'", HIGH);

    private static final List<String> HIGH_LEVEL_LIST = List.of(HIGH);

    private static final List<String> CANDIDATE_OPEN_STATUS = List.of("'OPENED'", "'ASSIGNED'", "'INTERVIEWED'");

    public static final String NULL = "null";

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
                    case LOW -> filter.add(fillFilterBuilder(positionSkill.skill().code(), LOW_LEVEL_LIST));
                    case MEDIUM -> filter.add(fillFilterBuilder(positionSkill.skill().code(), MID_LEVEL_LIST));
                    default -> filter.add(fillFilterBuilder(positionSkill.skill().code(), HIGH_LEVEL_LIST));
                }
            }
        }

        var query = String.format("MATCH (p:People)-[r:KNOWS]->(s:Skill) WHERE ALL(pair IN [%s] " +
                " WHERE (p)-[:KNOWS]->(:Skill {code: pair.skillcode}) " +
                "AND ANY (lvl IN pair.knowslevel WHERE (p)-[:KNOWS {level: lvl}]->(:Skill {code: pair.skillcode}))" +
                "AND p.assignable = TRUE )" +
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
                    "RETURN p,r.code,r.status,r.creationDate,n.code,ID(r)", people.code(), positionCode,people.code()+ "-" + people.employeeId(),EnumStatus.OPENED,LocalDate.now().toString());

            var candidate = client.query(candidateQuery).fetchAs(Candidate.class)
                                  .mappedBy((TypeSystem t, Record result) -> getCandidateBuilder(result).build())
                                  .one();

            candidate.ifPresent(candidateList::add);
        }

        return candidateList;
    }

    @Override
    public List<Candidate> findByPeopleandPosition(String positionCode, Long peopleCode) {

        var query = String.format("MATCH(p:People{code:%d})-[r:CANDIDATE]-(n:Position{code:'%s'}) " +
                                  "WHERE r.status IN %s RETURN p,r.code, r.introductionDate, r.resolutionDate, r.creationDate, r.status, ID(r), n",
                                  peopleCode, positionCode, CANDIDATE_OPEN_STATUS);

        return new ArrayList<>(client
                                 .query(query)
                                 .fetchAs(Candidate.class)
                                 .mappedBy((TypeSystem t, Record result) -> getCandidateBuilder(result).build())
                                 .all()
        );

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
               , peopleCode, positionCode, LocalDate.now(), position.getRole());
        client.query(query).run();

        var queryStatusOK = String.format("MATCH(p:People)-[r:CANDIDATE]-(s:Position) " +
                        "WHERE ID(r) = %d SET r.status = 'OK'"
                , openCandidate.id());
        client.query(queryStatusOK).run();

        var queryStatusKO = String.format("MATCH(p:People)-[r:CANDIDATE]-(n:Position{code:'%s'}) " +
                "WHERE NOT r.status = 'KO' AND NOT r.status = 'OK' SET r.status = 'KO'"
                , positionCode);
        client.query(queryStatusKO).run();

        var queryAssignableTrue = String.format("MATCH(p:People{code:%d}) " +
                "SET p.assignable = FALSE"
                , peopleCode);
        client.query(queryAssignableTrue).run();
    }

    @Override
    public List<Candidate> getCandidates(String positionCode) {
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
    public List<Candidate> getCandidates(Long peopleCode) {
        var query = String.format("MATCH(n:Position)-[r:CANDIDATE]-(p:People{code:%d}) " +
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
        if (!Objects.isNull(result.get("ID(r)")))
            candidateBuilder.id(result.get("ID(r)").asString());
        candidateBuilder.id(result.get("ID(r)").asString());
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
                     .birthDate(people.get("birthDate").asLocalDate())
                     .code(people.get("code").asLong())
                     .deleted(people.get("deleted").asBoolean())
                     .build();
    }

    private String fillFilterBuilder(final String skillCode, final List<String> levelList) {
        return String.format("{skillcode:'%s', knowslevel:[%s]}", skillCode, String.join(",", levelList));
    }

    private static BiFunction<TypeSystem, Record, People> getTypeSystemRecordPeopleBiFunction() {
        return (TypeSystem t, Record result) -> {
            var people = result.get("p");
            return People.builder()
                         .name(people.get("name").asString())
                         .surname(people.get("surname").asString())
                         .employeeId(people.get("employeeId").asString())
                         .birthDate(people.get("birthDate").asLocalDate())
                         .code(people.get("code").asLong())
                         .deleted(people.get("deleted").asBoolean())
                         .build();
        };
    }

}
