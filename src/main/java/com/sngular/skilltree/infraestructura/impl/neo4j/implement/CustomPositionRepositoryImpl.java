package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomPositionRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PositionExtendedView;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.driver.Record;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;


@Repository
public class CustomPositionRepositoryImpl implements CustomPositionRepository {

    private final Neo4jClient client;

    private static final String NULL = "null";

    private final static String QUERY = """
            MATCH (p:Position)
            OPTIONAL MATCH (p)-[manages:MANAGED]->(n:People)
            OPTIONAL MATCH (p)-[candidates:CANDIDATE]->(c:People)
            OPTIONAL MATCH (p)-[projects:FOR_PROJECT]->(k:Project)
            RETURN p,
                    COLLECT(DISTINCT {candidate: c, properties: properties(candidates)}) AS candidateData,
                   k,n
            """;

    public CustomPositionRepositoryImpl(final Neo4jClient client) {
        this.client = client;
    }

    private LocalDateTime getLocalDateTime(Object dateObject) {
        if (dateObject == null || "NULL".equalsIgnoreCase(dateObject.toString())) {
            return null;
        }
        return LocalDateTime.parse(dateObject.toString());
    }

    @Override
    public List<PositionExtendedView> getAllPositionExtended(){
        return new ArrayList<>(client
                .query(QUERY)
                .fetchAs(PositionExtendedView.class)
                .mappedBy((typeSystem, record) -> PositionExtendedView
                        .builder()
                        .name(record.get("p").get("name").asString())
                        .code(record.get("p").get("code").asString())
                        .mode(NULL.equalsIgnoreCase(StringUtils.upperCase(record.get("p").get("mode").asString())) ? null : StringUtils.upperCase(record.get("p").get("mode").asString()))
                        .role(record.get("p").get("role").asString())
                        .openingDate(NULL.equalsIgnoreCase(record.get("p").get("openingDate").asString()) ? null : record.get("p").get("openingDate").asLocalDateTime())
                        .priority(record.get("p").get("priority").asString())
                        .managedBy(getSafeValue(record, "n", "name"))
                        .projectCode(getSafeValue(record, "k", "code"))
                        .projectName(getSafeValue(record, "k", "name"))
                        .candidates(record.get("candidateData") != null ? null : record.get("candidateData").asList(value -> {
                            Map<String, Object> candidateProperties = value.get("candidateProperties").asMap();
                            return PositionExtendedView.CandidateView.builder()
                                    .interviewDate(getLocalDateTime(candidateProperties.get("interviewDate")))
                                    .introductionDate(getLocalDateTime(candidateProperties.get("introductionDate")))
                                    .resolutionDate(getLocalDateTime(candidateProperties.get("resolutionDate")))
                                    .status((StringUtils.upperCase((String) candidateProperties.get("status"))))
                                    .creationDate(getLocalDateTime(candidateProperties.get("creationDate")))
                                    .code((String) candidateProperties.get("code"))
                                    .build();
                        }))
                        .build())
                        .all());
    }

    private String getSafeValue(final Record record, final String root, final String prop) {
        String result = null;
        org.neo4j.driver.Value rootValue = record.get(root);

        if(root.equalsIgnoreCase("k")){
            if (rootValue.type().name().equals("NODE"))
                result = record.get(root).get(prop).asString();
        } else if (!"NULL".equalsIgnoreCase(record.get(root).asString())) {
            result = record.get(root).get(prop).asString();
        }
        return result;
    }
}
