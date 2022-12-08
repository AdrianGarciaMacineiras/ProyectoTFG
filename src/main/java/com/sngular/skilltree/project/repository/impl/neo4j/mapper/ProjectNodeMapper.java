package com.sngular.skilltree.project.repository.impl.neo4j.mapper;

import com.sngular.skilltree.person.repository.impl.neo4j.mapper.PersonNodeMapper;
import com.sngular.skilltree.project.model.Project;
import com.sngular.skilltree.project.repository.impl.neo4j.model.ProjectNode;
import com.sngular.skilltree.skill.repository.impl.neo4j.mapper.SkillNodeMapper;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(uses = {PersonNodeMapper.class, SkillNodeMapper.class})
public interface ProjectNodeMapper {

    @InheritInverseConfiguration
    ProjectNode toNode(Project project);

    Project fromNode(ProjectNode projectNode);

    List<Project> map(List<ProjectNode> all);
}
