package com.sngular.skilltree.application;

import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.model.Project;
import com.sngular.skilltree.infraestructura.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        validateExist(project.code());
        return projectRepository.save(project);
    }

    @Override
    public Project findByCode(Long projectcode) {
        var project = projectRepository.findByCode(projectcode);
        if (Objects.isNull(project) || project.deleted())
            throw new EntityNotFoundException("Project", projectcode);
        return project;
    }

    @Override
    public boolean deleteByCode(Long projectcode) {
        validateDoesNotExist(projectcode);
        return projectRepository.deleteByCode(projectcode);
    }

    @Override
    public Project findProject(Long projectcode) {
        var project = projectRepository.findProject(projectcode);
        if (Objects.isNull(project) || project.deleted())
            throw new EntityNotFoundException("Project", projectcode);
        return project;    }

    private void validateExist(Long code) {
        var oldProject = projectRepository.findByCode(code);
        if (!Objects.isNull(oldProject) && !oldProject.deleted()) {
            throw new EntityFoundException("Project", code);
        }
    }

    private void validateDoesNotExist(Long code) {
        var oldProject = projectRepository.findByCode(code);
        if (Objects.isNull(oldProject) || oldProject.deleted()) {
            throw new EntityNotFoundException("Project", code);
        }
    }
}
