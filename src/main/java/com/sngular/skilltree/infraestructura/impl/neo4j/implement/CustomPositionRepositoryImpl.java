package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomPositionRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PositionExtendedView;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomPositionRepositoryImpl implements CustomPositionRepository {

    private final Neo4jClient client;

    public static final String NULL = "null";

    private final static String QUERY = """
            MATCH(p:Position)-[r:MANAGED]-(n:People),
            (p)-[c:CANDIDATE]-(m:People),
            (p)-[f:FOR_PROJECT]-(k:Project)
            with p,r,n,k,collect({code: m.code, interviewDate:c.interviewDate, introductionDate:c.introductionDate,
            resolutionDate:c.resolutionDate, creationDate:c.creationDate, status:c.status}) as candidates
            return p,r,n,candidates,m,k
            """;

    public CustomPositionRepositoryImpl(final Neo4jClient client){this.client = client;}

    @Override
    public List<PositionExtendedView> getAllPositionExtended(){
        return new ArrayList<>(client
                .query(QUERY)
                .fetchAs(PositionExtendedView.class)
                .mappedBy((typeSystem, record) -> PositionExtendedView
                        .builder()
                        .name(record.get("p").get("name").asString())
                        .code(record.get("p").get("code").asString())
                        .mode(record.get("p").get("mode").asString())
                        .role(record.get("p").get("role").asString())
                        .openingDate(NULL.equalsIgnoreCase(record.get("p").get("openingDate").asString()) ? null : record.get("p").get("openingDate").asLocalDateTime())
                        .priority(record.get("p").get("priority").asString())
                        .managedBy(record.get("n").get("name").asString())
                        .projectCode(record.get("k").get("code").asString())
                        .candidates(record.get("candidates").asList(value -> PositionExtendedView
                                .CandidateView
                                .builder()
                                .interviewDate(NULL.equalsIgnoreCase(value.get("interviewDate").asString()) ? null : value.get("interviewDate").asLocalDateTime())
                                .introductionDate(NULL.equalsIgnoreCase(value.get("introductionDate").asString()) ? null : value.get("introductionDate").asLocalDateTime())
                                .resolutionDate(NULL.equalsIgnoreCase(value.get("resolutionDate").asString()) ? null : value.get("resolutionDate").asLocalDateTime())
                                .status(value.get("status").asString())
                                .creationDate(NULL.equalsIgnoreCase(value.get("creationDate").asString()) ? null : value.get("creationDate").asLocalDateTime())
                                .code(value.get("code").asString())
                                .build()))
                        .build())
                        .all());
    }
}
