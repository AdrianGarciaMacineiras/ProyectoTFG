package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CertificateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.KnowsRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.Role;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PeopleView;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PeopleView.KnowsView;
import com.sngular.skilltree.model.Certificate;
import com.sngular.skilltree.model.Knows;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Skill;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = {ResolveServiceNode.class, SkillNodeMapper.class})
public interface PeopleNodeMapper {

    @InheritInverseConfiguration
    @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "assigns", source = "assigns", qualifiedByName = {"resolveServiceNode", "mapToAssignedRelationship"})
        //@Mapping(target = "noClients", source = "noClients", qualifiedByName = {"resolveServiceNode", "mapToClientNode"})
        //@Mapping(target = "noProjects", source = "noProjects", qualifiedByName = {"resolveServiceNode", "mapToProjectNode"})
    PeopleNode toNode(People people);

    @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "assigns", source = "assigns", qualifiedByName = {"resolveServiceNode", "mapToAssignment"})
        //@Mapping(target = "noClients", source = "noClients", qualifiedByName = {"resolveServiceNode", "mapToClientString"})
        //@Mapping(target = "noProjects", source = "noProjects", qualifiedByName = {"resolveServiceNode", "mapToProjectString"})
    People fromNode(PeopleNode peopleNode);

    @Mapping(target = "work_with", source = "workWith")
    People fromView(PeopleView peopleNode);

    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "code", source = "skillNode.code")
    @Mapping(target = "name", source = "skillNode.name")
    Certificate certificateRelationshipToCertificate(CertificateRelationship certificateRelationship);

    @Mapping(target = "skillNode", source = "code", qualifiedByName = {"resolveServiceNode", "resolveCodeToSkillNode"})
    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    CertificateRelationship certificateToCertificateRelationship(Certificate certificate);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    com.sngular.skilltree.model.Role roleToRole(Role role);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    Role roleToRole1(com.sngular.skilltree.model.Role role);

    @Mapping(target = "code", source = "skill.code")
    @Mapping(target = "name", source = "skill.name")
    Knows knowsRelationshipToKnows(KnowsRelationship knowsRelationship);

    @Mapping(target = "code", source = "skill.code")
    @Mapping(target = "name", source = "skill.name")
    Knows knowsViewToKnows(KnowsView knowsView);

    List<Knows> knowsRelationshipListToKnowsList(List<KnowsRelationship> list);

    @Mapping(target = "skill", source = "code", qualifiedByName = {"resolveServiceNode", "resolveCodeToSkillNode"})
    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    KnowsRelationship knowsToKnowsRelationship(Knows knows);

    List<People> map(List<PeopleView> all);

    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = "name")
    Skill map(PeopleView.SkillView skillView);
}
