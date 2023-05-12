package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import com.sngular.skilltree.model.Project;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = {PeopleNodeMapper.class, SkillNodeMapper.class, ClientNodeMapper.class})
public interface ProjectNodeMapper {

    @InheritInverseConfiguration
    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    ProjectNode toNode(Project project);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "domain", source = "dominio")
    Project fromNode(ProjectNode projectNode);

    List<Project> map(List<ProjectNode> all);
}
