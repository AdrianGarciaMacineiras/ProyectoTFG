package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.PatchedProjectDTO;
import com.sngular.skilltree.api.model.ProjectDTO;
import com.sngular.skilltree.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = SkillMapper.class)
public interface ProjectMapper {

    ProjectDTO toProjectDTO(Project project);

    Project toProject(ProjectDTO projectDTO);

    List<ProjectDTO> toProjectsDTO(Collection<Project> projects);

    Project toProject(PatchedProjectDTO patchedProjectDTO);

    void update(@MappingTarget Project oldProject, Project newProject);
}
