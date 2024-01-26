package com.tfg.skilltree.application.implement;

import java.util.List;
import java.util.Objects;

import com.tfg.skilltree.application.ProjectService;
import com.tfg.skilltree.common.exceptions.EntityFoundException;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.infraestructura.ProjectRepository;
import com.tfg.skilltree.model.Project;
import com.tfg.skilltree.model.views.ProjectNamesView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    @Override
    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    @Override
    //@Cacheable(cacheNames = "projectNamesView")
    public List<ProjectNamesView> getAllNames() {
        return projectRepository.findAllNames();
    }

    @Override
    //@CacheEvict(cacheNames = "projectNamesView")
    public Project create(final Project project) {
        validateExist(project.code());
        return projectRepository.save(project);
    }

    @Override
    public Project findByCode(final String projectCode) {
        var project = projectRepository.findByCode(projectCode);
        if (Objects.isNull(project) || project.deleted())
            throw new EntityNotFoundException("Project", projectCode);
        return project;
    }

    @Override
    public boolean deleteByCode(final String projectCode) {
        validateDoesNotExist(projectCode);
        return projectRepository.deleteByCode(projectCode);
    }

    @Override
    public Project findProject(final String projectCode) {
        var project = projectRepository.findProject(projectCode);
        if (Objects.isNull(project) || project.deleted())
            throw new EntityNotFoundException("Project", projectCode);
        return project;
    }

    private void validateExist(final String code) {
        var oldProject = projectRepository.findByCode(code);
        if (!Objects.isNull(oldProject) && !oldProject.deleted()) {
            throw new EntityFoundException("Project", code);
        }
    }

    private void validateDoesNotExist(final String code) {
        var oldProject = projectRepository.findByCode(code);
        if (Objects.isNull(oldProject) || oldProject.deleted()) {
            throw new EntityNotFoundException("Project", code);
        }
    }
}
