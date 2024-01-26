package com.tfg.skilltree.application.updater.implement;

import java.util.Objects;

import com.tfg.skilltree.application.updater.ProjectUpdater;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.contract.mapper.ProjectMapper;
import com.tfg.skilltree.infraestructura.ProjectRepository;
import com.tfg.skilltree.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectUpdaterImpl implements ProjectUpdater {

    private final ProjectRepository projectRepository;

    private final ProjectMapper mapper;

    @Override
    public Project update(String projectCode, Project newProject) {
        validate(projectCode);
        return projectRepository.save(newProject);
    }

    @Override
    public Project patch(String projectCode, Project patchedProject) {
        validate(projectCode);
        var oldProject = projectRepository.findByCode(projectCode);
        var project = mapper.patch(patchedProject, oldProject);
        return projectRepository.save(project);
    }

    private void validate(String code) {
        var oldProject = projectRepository.findByCode(code);
        if (Objects.isNull(oldProject) || oldProject.deleted()) {
            throw new EntityNotFoundException("Project", code);
        }
    }
}
