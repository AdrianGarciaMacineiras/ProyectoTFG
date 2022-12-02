package com.sngular.skilltree.project.repository.impl.neo4j.mapper;

import com.sngular.skilltree.project.model.Project;
import com.sngular.skilltree.project.repository.impl.neo4j.model.ProjectNode;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper
public interface ProjectNodeMapper {

    @InheritInverseConfiguration
    ProjectNode toNode(Project project);

    Project fromNode(ProjectNode projectNode);

    List<Project> map(List<ProjectNode> all);
}
