package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomProjectRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.ProjectNamesView;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomProjectRepositoryImpl implements CustomProjectRepository {

    private final Neo4jClient client;

    private final static String queryAllProjectNames = """
            MATCH(p:Project) RETURN p.code, p.name
            """ ;

    public CustomProjectRepositoryImpl(final Neo4jClient client) {
        this.client = client;
    }

    @Override
    public List<ProjectNamesView> getAllProjectNames(){
        return new ArrayList<>(client
                .query(queryAllProjectNames)
                .fetchAs(ProjectNamesView.class)
                .mappedBy((typeSystem, record) -> ProjectNamesView
                        .builder()
                        .code(record.get("p.code").asString())
                        .name(record.get("p.name").asString())
                        .build())
                .all());
    }
}
