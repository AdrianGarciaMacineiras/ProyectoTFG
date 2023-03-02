package com.sngular.skilltree.contract.mapper;


import com.sngular.skilltree.api.model.PatchedProjectDTO;
import com.sngular.skilltree.api.model.ProjectDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring", uses = {SkillMapper.class, ResolveService.class})
public interface ProjectMapper {

    @Mapping(source = "client.code", target = "clientCode")
    @Mapping(source = "skills", target = "skills", qualifiedByName = {"resolveService", "resolveSkillCodeList"})
    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    ProjectDTO toProjectDTO(Project project);

    @Mapping(source = "skills", target = "skills", qualifiedByName = {"resolveService", "resolveCodeSkillList"})
    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "client", source = "clientCode", qualifiedByName = {"resolveService", "resolveCodeClient"})
    Project toProject(ProjectDTO projectDTO);

    List<ProjectDTO> toProjectsDTO(Collection<Project> projects);

    @Mapping(source = "skills", target = "skills", qualifiedByName = {"resolveService", "resolveCodeSkillList"})
    Project toProject(PatchedProjectDTO patchedProjectDTO);

    //void update(@MappingTarget Project oldProject, Project newProject);

    @Named("update")
    default Project update(Project newProject, Project oldProject) {
        Project.ProjectBuilder projectBuilder = oldProject.toBuilder();

        Project project = projectBuilder
                .code(oldProject.code())
                .duration((newProject.duration() == null) ? oldProject.duration() : newProject.duration())
                .initDate((newProject.initDate() == null) ? oldProject.initDate() : newProject.initDate())
                .tag((newProject.tag() == null) ? oldProject.tag() : newProject.tag())
                .skills((newProject.skills() == null) ? oldProject.skills() : newProject.skills())
                .name((newProject.name() == null) ? oldProject.name() : newProject.name())
                .roles((newProject.roles() == null) ? oldProject.roles() : newProject.roles())
                .area((newProject.area() == null) ? oldProject.area() : newProject.area())
                .historic((newProject.historic() == null) ? oldProject.historic() : newProject.historic())
                .endDate((newProject.endDate() == null) ? oldProject.endDate() : newProject.endDate())
                .domain((newProject.domain() == null) ? oldProject.domain() : newProject.domain())
                .client((newProject.client() == null) ? oldProject.client() : newProject.client())
                .desc((newProject.desc() == null) ? oldProject.desc() : newProject.desc())
                .guards((newProject.guards() == null) ? oldProject.guards() : newProject.guards())
                .build();

        return project;
    };
}
