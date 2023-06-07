package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.ProjectService;
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
public class ProjectServiceImpl implements ProjectService {

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
    public Project findByCode(String projectCode) {
        var project = projectRepository.findByCode(projectCode);
        if (Objects.isNull(project) || project.deleted())
            throw new EntityNotFoundException("Project", projectCode);
        return project;
    }

    @Override
    public boolean deleteByCode(String projectCode) {
        validateDoesNotExist(projectCode);
        return projectRepository.deleteByCode(projectCode);
    }

    @Override
    public Project findProject(String projectCode) {
        var project = projectRepository.findProject(projectCode);
        if (Objects.isNull(project) || project.deleted())
            throw new EntityNotFoundException("Project", projectCode);
        return project;
    }

    private void validateExist(String code) {
        var oldProject = projectRepository.findByCode(code);
        if (!Objects.isNull(oldProject) && !oldProject.deleted()) {
            throw new EntityFoundException("Project", code);
        }
    }

    private void validateDoesNotExist(String code) {
        var oldProject = projectRepository.findByCode(code);
        if (Objects.isNull(oldProject) || oldProject.deleted()) {
            throw new EntityNotFoundException("Project", code);
        }
    }
}
