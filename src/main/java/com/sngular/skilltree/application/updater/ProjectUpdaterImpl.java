package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.contract.mapper.ProjectMapper;
import com.sngular.skilltree.infraestructura.ProjectRepository;
import com.sngular.skilltree.model.Project;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectUpdaterImpl implements ProjectUpdater{

    private final ProjectRepository projectRepository;

    private final ProjectMapper mapper;

    @Override
    public Project update(Integer projectcode, Project newProject) {
        var oldProject = projectRepository.findByCode(projectcode);
        //mapper.update(oldProject,newProject);
        return projectRepository.save(newProject);
    }

    @Override
    public Project patch(Integer projectcode, Project patchedProject) {
        var oldProject = projectRepository.findByCode(projectcode);
        var project = mapper.update(patchedProject, oldProject);
        return projectRepository.save(project);
    }

}
