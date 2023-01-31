package com.sngular.skilltree.contract.mapper;


import com.sngular.skilltree.api.model.PatchedProjectDTO;
import com.sngular.skilltree.api.model.ProjectDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {SkillMapper.class, ResolveService.class})
public interface ProjectMapper {

    ProjectDTO toProjectDTO(Project project);

    @Mapping(source = "skills", target = "skills", qualifiedByName = {"resolveService", "resolveCodeSkill"})
    Project toProject(ProjectDTO projectDTO);

    List<ProjectDTO> toProjectsDTO(Collection<Project> projects);

    @Mapping(source = "skills", target = "skills", qualifiedByName = {"resolveService", "resolveCodeSkill"})
    Project toProject(PatchedProjectDTO patchedProjectDTO);

    void update(@MappingTarget Project oldProject, Project newProject);
}
