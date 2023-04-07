package com.sngular.skilltree.infraestructura.impl.neo4j.implement;


import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.impl.neo4j.ProjectCrudRepository;
import com.sngular.skilltree.model.Project;
import com.sngular.skilltree.infraestructura.ProjectRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.ProjectNodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Objects;

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
    public Project findByCode(Long projectcode) {
        return mapper.fromNode(crud.findByCode(projectcode));
    }

    @Override
    public boolean deleteByCode(Long projectcode) {
        var node = crud.findByCode(projectcode);
        node.setDeleted(true);
        crud.save(node);
        return true;
    }

    @Override
    public List<Project> findByDeletedIsFalse() {
        return mapper.map(crud.findByDeletedIsFalse());
    }

    @Override
    public Project findProject(Long projectcode) {
        return mapper.fromNode(crud.findProject(projectcode));
    }


}
