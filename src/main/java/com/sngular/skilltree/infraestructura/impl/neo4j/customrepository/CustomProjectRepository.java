package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.ProjectNamesView;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomProjectRepository {

    List<ProjectNamesView> getAllProjectNames();
}
