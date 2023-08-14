package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomPeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PeopleExtendedView;
import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
public class CustomPeopleRepositoryImpl implements CustomPeopleRepository {

    private final Neo4jClient client;

    private final static String QUERY = """
            match (p:Person {deleted: false})-[r:KNOWS]-(s:Skill)
            with p, collect({code:s.code, name:s.name, experience:r.experience, level:r.level, primary:r.primary}) as skill
            return p, skills
            """;

    public CustomPeopleRepositoryImpl(final Neo4jClient client) {
        this.client = client;
    }

    @Override
    public PeopleExtendedView getAllPeopleExtended() {
        client
                .query(QUERY)
                .fetchAs(PeopleExtendedView.class)
                .mappedBy((typeSystem, record) -> {
                    return PeopleExtendedView
                            .builder()
                            .name(record.get("p.name").asString())
                            .title(record.get("p.title").asString())
                            .knows(record.get("skills").asList(value -> PeopleExtendedView
                                    .KnowsView
                                    .builder()
                                    .level(value.get("level").asString())
                                    .primary(value.get("primary").asBoolean())
                                    .experience(value.get("experience").asInt())
                                    .code(value.get("code").asString())
                                    .name(value.get("name").asString())
                                    .build()))
                            .surname(record.get("p.surname").asString())
                            .assignable(record.get("p.assignable").asBoolean())
                            .employeeId(record.get("p.employeeId").asString())
                            .code(record.get("p.code").asString())
                            .build();
                })
                .all();
        return null;
    }
}
