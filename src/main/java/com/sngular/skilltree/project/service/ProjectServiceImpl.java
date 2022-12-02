package com.sngular.skilltree.project.service;

import com.sngular.skilltree.project.mapper.ProjectMapper;
import com.sngular.skilltree.project.model.Project;
import com.sngular.skilltree.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService{

    private final ProjectRepository projectRepository;

    private final ProjectMapper mapper;
    @Override
    public List<Project> getALl() {
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

    @Override
    public Project update(String projectcode, Project newProject) {
        var oldProject = projectRepository.findByCode(projectcode);
        mapper.update(oldProject,newProject);
        return projectRepository.save(oldProject);
    }

    @Override
    public Project patch(String projectcode, Project patchedProject) {
        var oldProject = projectRepository.findByCode(projectcode);
        mapper.update(oldProject,patchedProject);
        return projectRepository.save(oldProject);
    }

    private void validate(Project project) {
    }
}
