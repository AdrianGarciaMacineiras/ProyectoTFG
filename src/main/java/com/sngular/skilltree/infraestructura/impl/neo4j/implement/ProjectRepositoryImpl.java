package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.ProjectRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.ProjectCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.ProjectNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import com.sngular.skilltree.model.Project;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository {

    private final ProjectCrudRepository crud;

    private final ProjectNodeMapper mapper;

    @Override
    public List<Project> findAll() {
        return mapper.map(crud.findByDeletedIsFalse());
    }

    @Override
    public Project save(Project project) {
        var projectNode = mapper.toNode(project);
        if (Objects.isNull(projectNode.getClient()) || projectNode.getClient().isDeleted()) {
            throw new EntityNotFoundException("Client", projectNode.getClient().getCode());
        }

        return mapper.fromNode(crud.save(projectNode));
    }

    @Override
    public Project findByCode(String projectCode) {
        ProjectNode project;
        if (NumberUtils.isCreatable(projectCode)) {
            project = crud.findByCode(projectCode);
        } else {
            project = crud.findByName(projectCode);
        }
        return mapper.fromNode(project);
    }

    @Override
    public boolean deleteByCode(String projectCode) {
        var node = crud.findByCode(projectCode);
        node.setDeleted(true);
        crud.save(node);
        return true;
    }

    @Override
    public List<Project> findByDeletedIsFalse() {
        return mapper.map(crud.findByDeletedIsFalse());
    }

    @Override
    public Project findProject(String projectCode) {
        return mapper.fromNode(crud.findProject(projectCode));
    }


}
