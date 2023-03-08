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
        return mapper.map(crud.findAll());
    }

    @Override
    public Project save(Project project) {
        return mapper.fromNode(crud.save(mapper.toNode(project)));
    }

    @Override
    public Project findByCode(Integer projectcode) {
        return mapper.fromNode(crud.findByCode(projectcode));
    }

    @Override
    public boolean deleteByCode(Integer projectcode) {
        var node = crud.findByCode(projectcode);
        crud.delete(node);
        return true;
    }
}
