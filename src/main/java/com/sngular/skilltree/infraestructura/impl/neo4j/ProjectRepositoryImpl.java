package com.sngular.skilltree.infraestructura.impl.neo4j;


import com.sngular.skilltree.model.Project;
import com.sngular.skilltree.infraestructura.ProjectRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.ProjectNodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

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
        return mapper.fromNode(crud.save(mapper.toNode(project)));
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
