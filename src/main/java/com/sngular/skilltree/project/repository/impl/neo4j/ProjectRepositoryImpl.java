package com.sngular.skilltree.project.repository.impl.neo4j;


import com.sngular.skilltree.project.model.Project;
import com.sngular.skilltree.project.repository.ProjectRepository;
import com.sngular.skilltree.project.repository.impl.neo4j.mapper.ProjectNodeMapper;
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
    public Project findByCode(String projectcode) {
        return mapper.fromNode(crud.findByCode(projectcode));
    }

    @Override
    public boolean deleteByCode(String projectcode) {
        var node = crud.findByCode(projectcode);
        crud.delete(node);
        return true;
    }
}
