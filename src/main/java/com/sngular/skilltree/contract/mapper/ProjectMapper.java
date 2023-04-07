package com.sngular.skilltree.contract.mapper;

import com.sngular.skilltree.api.model.PatchedProjectDTO;
import com.sngular.skilltree.api.model.ProjectDTO;
import com.sngular.skilltree.application.ResolveService;
import com.sngular.skilltree.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

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

    @Named("patch")
    default Project patch(Project newProject, Project oldProject) {
        Project.ProjectBuilder projectBuilder = oldProject.toBuilder();

        return projectBuilder
                .code(oldProject.code())
                .duration((Objects.isNull(newProject.duration())) ? oldProject.duration() : newProject.duration())
                .initDate((Objects.isNull(newProject.initDate())) ? oldProject.initDate() : newProject.initDate())
                .tag((Objects.isNull(newProject.tag())) ? oldProject.tag() : newProject.tag())
                .skills((Objects.isNull(newProject.skills())) ? oldProject.skills() : newProject.skills())
                .name((Objects.isNull(newProject.name())) ? oldProject.name() : newProject.name())
                .roles((Objects.isNull(newProject.roles())) ? oldProject.roles() : newProject.roles())
                .area((Objects.isNull(newProject.area())) ? oldProject.area() : newProject.area())
                .historic((Objects.isNull(newProject.historic())) ? oldProject.historic() : newProject.historic())
                .endDate((Objects.isNull(newProject.endDate())) ? oldProject.endDate() : newProject.endDate())
                .domain((Objects.isNull(newProject.domain())) ? oldProject.domain() : newProject.domain())
                .client((Objects.isNull(newProject.client())) ? oldProject.client() : newProject.client())
                .desc((Objects.isNull(newProject.desc())) ? oldProject.desc() : newProject.desc())
                .guards((Objects.isNull(newProject.guards())) ? oldProject.guards() : newProject.guards())
                .build();

    };
}
