package com.sngular.skilltree.project.mapper;

import com.sngular.skilltree.api.model.PatchedProjectDTO;
import com.sngular.skilltree.api.model.ProjectDTO;
import com.sngular.skilltree.person.mapper.PersonMapper;
import com.sngular.skilltree.person.model.Person;
import com.sngular.skilltree.project.model.Project;
import com.sngular.skilltree.skill.mapper.SkillMapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import java.util.Collection;
import java.util.List;

@Mapper(uses = {PersonMapper.class, SkillMapper.class})
public interface ProjectMapper {

    ProjectDTO toProjectDTO(Project project);

    Project toProject(ProjectDTO projectDTO);

    List<ProjectDTO> toProjectsDTO(Collection<Project> projects);

    Project toProject(PatchedProjectDTO patchedProjectDTO);

    void update(@MappingTarget Project oldProject, Project newProject);
}
