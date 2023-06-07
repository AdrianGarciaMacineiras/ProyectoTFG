package com.sngular.skilltree.application.updater.implement;

import com.sngular.skilltree.application.updater.ProjectUpdater;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.mapper.ProjectMapper;
import com.sngular.skilltree.infraestructura.ProjectRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.ProjectCrudRepository;
import com.sngular.skilltree.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

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
