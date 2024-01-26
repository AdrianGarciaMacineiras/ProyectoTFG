package com.tfg.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.tfg.skilltree.common.config.CommonMapperConfiguration;
import com.tfg.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.tfg.skilltree.infraestructura.impl.neo4j.model.ProjectNode;
import com.tfg.skilltree.infraestructura.impl.neo4j.model.ProjectRolesNode;
import com.tfg.skilltree.model.Project;
import com.tfg.skilltree.model.ProjectRoles;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = {PeopleNodeMapper.class, SkillNodeMapper.class, ClientNodeMapper.class, ResolveServiceNode.class})
public interface ProjectNodeMapper {

    @InheritInverseConfiguration
    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    ProjectNode toNode(Project project);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "endDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "domain", source = "domain")
    Project fromNode(ProjectNode projectNode);

    List<Project> map(List<ProjectNode> all);

    @Mapping(target = "id")
    ProjectRoles rolesToRolesNode(ProjectRolesNode rol);

    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    ProjectRolesNode rolesNodeToRoles(ProjectRoles rol);

    List<com.tfg.skilltree.model.views.ProjectNamesView> mapProjectNames(List<com.tfg.skilltree.infraestructura.impl.neo4j.querymodel.ProjectNamesView> all);
}
