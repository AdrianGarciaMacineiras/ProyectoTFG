package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomPositionRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PositionExtendedView;
import org.apache.commons.lang3.StringUtils;
import org.neo4j.driver.Record;
import org.neo4j.driver.Value;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;


@Repository
public class CustomPositionRepositoryImpl implements CustomPositionRepository {

    private final static Pattern ISO_DATE_TIME = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}Z");

    private final Neo4jClient client;

    private static final String NULL = "null";

    private final static String QUERY = """
            MATCH (p:Position)
            OPTIONAL MATCH (p)-[manages:MANAGED]->(n:People)
            OPTIONAL MATCH (p)-[candidates:CANDIDATE]->(c:People)
            OPTIONAL MATCH (p)-[projects:FOR_PROJECT]->(k:Project)
            RETURN p, candidates, c, k, n
            """;

    public CustomPositionRepositoryImpl(final Neo4jClient client) {
        this.client = client;
    }

    private LocalDateTime getLocalDateTime(Object dateObject) {
        if (dateObject == null || "NULL".equalsIgnoreCase(dateObject.toString())) {
            return null;
        }
        if (ISO_DATE_TIME.matcher(dateObject.toString()).matches()) {
            return LocalDateTime.parse(dateObject.toString(), DateTimeFormatter.ISO_DATE_TIME);
        } else {
            return LocalDate.parse(dateObject.toString()).atStartOfDay();
        }
    }

    public List<PositionExtendedView> getAllPositionExtended() {
        var aux = new ArrayList<>(client
                .query(QUERY)
                .fetchAs(PositionExtendedView.class)
                .mappedBy((typeSystem, record) -> {
                    PositionExtendedView.PositionExtendedViewBuilder builder = PositionExtendedView.builder()
                            .name(record.get("p").get("name").asString())
                            .code(record.get("p").get("code").asString())
                            .mode(NULL.equalsIgnoreCase(StringUtils.upperCase(record.get("p").get("mode").asString())) ? null : StringUtils.upperCase(record.get("p").get("mode").asString()))
                            .role(record.get("p").get("role").asString())
                            .openingDate(getLocalDateTime(record.get("p").get("initDate")))
                            .priority(record.get("p").get("priority").asString())
                            .projectCode(getSafeValue(record, "k", "code"))
                            .projectName(getSafeValue(record, "k", "name"))
                            .managedBy(getSafeValue(record, "n", "name"));

                    List<PositionExtendedView.CandidateView> candidatesList = new ArrayList<>();
                    if (record.get("c").type().name().equals("NODE")) {
                        candidatesList.add(PositionExtendedView.CandidateView.builder()
                                .interviewDate(getLocalDateTime(getSafeObject(record, "candidates", "interviewDate")))
                                .introductionDate(getLocalDateTime(getSafeObject(record, "candidates", "introductionDate")))
                                .resolutionDate(getLocalDateTime(getSafeObject(record, "candidates", "resolutionDate")))
                                .status(StringUtils.upperCase(getSafeValue(record, "candidates", "status")))
                                .creationDate(getLocalDateTime(getSafeObject(record, "candidates", "creationDate")))
                                .code(getSafeValue(record, "candidates", "code"))
                                .candidateCode(getSafeValue(record,"c", "employeeId"))
                                .build());
                    }

                    builder.candidates(candidatesList);
                    return builder.build();
                })
                .all());

        Map<String, PositionExtendedView> positionMap = new HashMap<>();

        aux.forEach(position ->
                positionMap.compute(position.code(), (code, aggPosition) -> {
                    if(Objects.isNull(aggPosition)){
                        return position;
                    } else {
                        var candidatesList = new ArrayList<>(aggPosition.candidates());
                        candidatesList.addAll(position.candidates());
                        aggPosition = aggPosition.toBuilder().candidates(candidatesList).build();
                    }
                    return aggPosition;
                })
        );


        return new ArrayList<>(positionMap.values());
    }

    private Value getSafeObject(final Record record, final String root, final String prop) {
        org.neo4j.driver.Value result = null;
        org.neo4j.driver.Value rootValue = record.get(root);

        if (!rootValue.isNull())
            result = record.get(root).get(prop);
        return result;
    }

    private String getSafeValue(final Record record, final String root, final String prop) {
        String result = null;
        org.neo4j.driver.Value rootValue = record.get(root);

        if(root.equalsIgnoreCase("k") || root.equalsIgnoreCase("c")) {
            if (rootValue.type().name().equals("NODE"))
                result = record.get(root).get(prop).asString();
        } else if (rootValue.type().name().equals("RELATIONSHIP") && !rootValue.isNull()){
            result = record.get(root).get(prop).asString();
        } else if (!"NULL".equalsIgnoreCase(rootValue.asString()) || !rootValue.isNull()) {
            result = record.get(root).get(prop).asString();
        }
        return result;
    }
}