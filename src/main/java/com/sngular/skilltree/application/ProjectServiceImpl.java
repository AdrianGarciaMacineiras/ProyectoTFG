package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Project;
import com.sngular.skilltree.infraestructura.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project create(Project project) {
        validate(project);
        return projectRepository.save(project);
    }

    @Override
    public Project findByCode(String projectcode) {
        return projectRepository.findByCode(projectcode);
    }

    @Override
    public boolean deleteByCode(String projectcode) {
        return projectRepository.deleteByCode(projectcode);
    }

    private void validate(Project project) {
    }
}
