package com.sngular.skilltree.application.implement;

import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.application.ProjectService;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.ProjectRepository;
import com.sngular.skilltree.model.Project;
import com.sngular.skilltree.model.views.PeopleNamesView;
import com.sngular.skilltree.model.views.ProjectNamesView;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @Cacheable(cacheNames = "projectNamesView")
    public List<ProjectNamesView> getAllNames() {
        return projectRepository.findAllNames();
    }

    @Override
    @CacheEvict(cacheNames = "projectNamesView")
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
