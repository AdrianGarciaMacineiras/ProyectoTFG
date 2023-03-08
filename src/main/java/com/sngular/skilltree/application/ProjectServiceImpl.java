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
    public Project findByCode(Integer projectcode) {
        return projectRepository.findByCode(projectcode);
    }

    @Override
    public boolean deleteByCode(Integer projectcode) {
        validateDoesntExist(projectcode);
        return projectRepository.deleteByCode(projectcode);
    }

    private void validateExist(Integer code) {
        var oldProject = projectRepository.findByCode(code);
        if (!Objects.isNull(oldProject)) {
            throw new EntityFoundException("Project", code);
        }
    }

    private void validateDoesntExist(Integer code) {
        var oldProject = projectRepository.findByCode(code);
        if (Objects.isNull(oldProject)) {
            throw new EntityNotFoundException("Project", code);
        }
    }
}
