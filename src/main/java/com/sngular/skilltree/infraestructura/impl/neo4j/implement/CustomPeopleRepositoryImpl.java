package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomPeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PeopleExtendedView;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomPeopleRepositoryImpl implements CustomPeopleRepository {

    private final Neo4jClient client;

    private final static String QUERY = """
            match (t:Team)-[rt:MEMBER_OF]-(p:People {deleted: false})-[r:KNOWS]-(s:Skill)
            with t, p, collect({code:s.code, name:s.name, experience:r.experience, level:r.level, primary:r.primary}) as skills
            return t, p, skills
            """;

    public CustomPeopleRepositoryImpl(final Neo4jClient client) {
        this.client = client;
    }

    @Override
    public List<PeopleExtendedView> getAllPeopleExtended() {
        return new ArrayList<>(client
                .query(QUERY)
                .fetchAs(PeopleExtendedView.class)
                .mappedBy((typeSystem, record) -> PeopleExtendedView
                        .builder()
                        .name(record.get("p").get("name").asString())
                        .title(record.get("p").get("title").asString())
                        .teamShortName(record.get("t").get("shortName").asString())
                        .knows(record.get("skills").asList(value -> PeopleExtendedView
                                .KnowsView
                                .builder()
                                .level(StringUtils.upperCase(value.get("level").asString()))
                                .primary(Boolean.TRUE.equals(value.get("primary", Boolean.FALSE)))
                                .experience(value.get("experience").asInt())
                                .code(value.get("code").asString())
                                .name(value.get("name").asString())
                                .build()))
                        .surname(record.get("p").get("surname").asString())
                        .assignable(Boolean.TRUE.equals(record.get("p").get("assignable", Boolean.FALSE)))
                        .employeeId(record.get("p").get("employeeId").asString())
                        .code(record.get("p").get("code").asString())
                        .build())
                .all());
    }
}
