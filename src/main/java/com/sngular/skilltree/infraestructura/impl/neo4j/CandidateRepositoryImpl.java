package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.contract.mapper.OpportunityMapper;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.customRepository.CandidateRelationshipProjection;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.CandidateNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.OpportunityNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.model.*;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.types.TypeSystem;
import org.neo4j.driver.Record;
import org.springframework.stereotype.Repository;
import org.springframework.data.neo4j.core.Neo4jClient;


import java.util.*;

@Repository
@RequiredArgsConstructor
public class CandidateRepositoryImpl implements CandidateRepository {

    private final CandidateCrudRepository crud;

    private final OpportunityCrudRepository opportunityCrudRepository;

    private final OpportunityNodeMapper opportunityNodeMapper;

    private final PeopleNodeMapper peopleNodeMapper;

    private final CandidateNodeMapper mapper;

    private final Neo4jClient client;

    @Override
    public List<Candidate> findAll() {
        var opportunityNodeList = opportunityCrudRepository.findAll();
        List<CandidateRelationship> candidateRelationshipList = new ArrayList<>();
        Candidate.CandidateBuilder candidateBuilder = Candidate.builder();

        for (var opportunityNode : opportunityNodeList){
            candidateRelationshipList.addAll(opportunityNode.getCandidates());
            candidateBuilder.opportunity(opportunityNodeMapper.fromNode(opportunityCrudRepository.findByCode(opportunityNode.getCode())));
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
        //node.setDeleted(true);
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

                        var knows = Knows.builder()
                                .code(record.get("k.name").asString())
                                .experience(record.get("s").get("experience").asInt())
                                .level(record.get("s").get("level").asString())
                                .primary(record.get("s").get("primary").asBoolean())
                                .build();

                            var candidateBuilder = Candidate.builder();
                            candidateBuilder.code(record.get("r.code").asString());
                            candidateBuilder.status((record.get("r.status").asString().equalsIgnoreCase("null")) ? EnumStatus.KO : EnumStatus.valueOf(record.get("r.status").asString()));
                            candidateBuilder.introductionDate((record.get("r.introductionDate").asString() == "null") ? null : record.get("r.introductionDate").asLocalDate());
                            candidateBuilder.resolutionDate((record.get("r.resolutionDate").asString() == "null") ? null : record.get("r.resolutionDate").asLocalDate());

                            var peopleBuilder = People.builder()
                                    .name(record.get("p").get("name").asString())
                                    .surname(record.get("p").get("surname").asString())
                                    .employeeId(record.get("p").get("employeeId").asString())
                                    .birthDate(record.get("p").get("birthDate").asLocalDate())
                                    .code(record.get("p").get("code").asLong())
                                    .deleted(record.get("p").get("deleted").asBoolean())
                                    .build();

                            candidateBuilder.candidate(peopleBuilder);
                            candidateBuilder.skills(List.of(knows));
                            candidateBuilder.opportunity(opportunityNodeMapper.fromNode(opportunityCrudRepository.findOpportunity(record.get("n.code").asString())));

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

//    @Override
//    public List<Candidate> findByDeletedIsFalse() {
//        return mapper.map(crud.findByDeletedIsFalse());
//    }
}
