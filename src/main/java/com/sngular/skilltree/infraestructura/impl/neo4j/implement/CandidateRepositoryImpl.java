package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.CandidateCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PositionCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.CandidateNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PositionNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import com.sngular.skilltree.model.*;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.types.TypeSystem;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Repository;
import org.springframework.data.neo4j.core.Neo4jClient;


import java.util.*;

@Repository
@RequiredArgsConstructor
public class CandidateRepositoryImpl implements CandidateRepository {

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
                .query("MATCH (o:Skill)-[m:NEEDS]-(n:Opportunity)-[r:CANDIDATE]-(p)-[s:KNOWS]-(k:Skill) " +
                        "WHERE o.code=k.code RETURN n.code, r.code, r.status, r.introductionDate, r.resolutionDate, p,s,k.name")
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

    private Candidate.CandidateBuilder getCandidateBuilder(Record record) {
        Knows knows = getKnows(record);

        var candidateBuilder = Candidate.builder();
        candidateBuilder.code(record.get("r.code").asString());
        candidateBuilder.status((record.get("r.status").asString().equalsIgnoreCase("null")) ? EnumStatus.KO : EnumStatus.valueOf(record.get("r.status").asString()));
        candidateBuilder.introductionDate((record.get("r.introductionDate").asString() == "null") ? null : record.get("r.introductionDate").asLocalDate());
        candidateBuilder.resolutionDate((record.get("r.resolutionDate").asString() == "null") ? null : record.get("r.resolutionDate").asLocalDate());

        People peopleBuilder = getPeople(record);

        candidateBuilder.candidate(peopleBuilder);
        candidateBuilder.skills(List.of(knows));
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

    private static Knows getKnows(Record record) {
        return Knows.builder()
                .code(record.get("k.name").asString())
                .experience(record.get("s").get("experience").asInt())
                .level(record.get("s").get("level").asString())
                .primary(record.get("s").get("primary").asBoolean())
                .build();
    }

}
