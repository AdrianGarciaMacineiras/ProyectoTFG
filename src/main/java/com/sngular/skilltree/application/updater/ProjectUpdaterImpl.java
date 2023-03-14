package com.sngular.skilltree.application.updater;

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
public class ProjectUpdaterImpl implements ProjectUpdater{

    private final ProjectRepository projectRepository;

    private final ProjectMapper mapper;

    private final ProjectCrudRepository crud;

    @Override
    public Project update(Long projectcode, Project newProject) {
        validate(projectcode);
        crud.detachDelete(projectcode);
        return projectRepository.save(newProject);
    }

    @Override
    public Project patch(Long projectcode, Project patchedProject) {
        validate(projectcode);
        var oldProject = projectRepository.findByCode(projectcode);
        var project = mapper.update(patchedProject, oldProject);
        crud.detachDelete(projectcode);
        return projectRepository.save(project);
    }

    private void validate(Long code) {
        var oldProject = projectRepository.findByCode(code);
        if (Objects.isNull(oldProject) || oldProject.deleted()) {
            throw new EntityNotFoundException("Project", code);
        }
    }
}
