package com.tfg.skilltree.infraestructura.impl.neo4j.implement;

import java.util.List;
import java.util.Objects;

import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.infraestructura.ProjectRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.ProjectCrudRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.customrepository.CustomProjectRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.mapper.ProjectNodeMapper;
import com.tfg.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import com.tfg.skilltree.model.Project;
import com.tfg.skilltree.model.views.ProjectNamesView;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectRepositoryImpl implements ProjectRepository {

    private final ProjectCrudRepository crud;

    private final ProjectNodeMapper mapper;

    private final CustomProjectRepository customCrud;

    @Override
    public List<Project> findAll() {
        var aux = crud.findByDeletedIsFalse();
        return mapper.map(aux);
    }

    @Override
    public List<ProjectNamesView> findAllNames() {
        return mapper.mapProjectNames(customCrud.getAllProjectNames());
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
        if(Objects.isNull(project))
            return null;
        else
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
