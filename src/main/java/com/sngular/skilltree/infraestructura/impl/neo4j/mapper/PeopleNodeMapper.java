package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CertificateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.KnowsRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.Role;
import com.sngular.skilltree.model.Certificate;
import com.sngular.skilltree.model.Knows;
import com.sngular.skilltree.model.People;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = {ResolveServiceNode.class, SkillNodeMapper.class})
public interface PeopleNodeMapper {

    @InheritInverseConfiguration
    @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "assigns", source = "assigns", qualifiedByName = {"resolveServiceNode", "mapToAssignedRelationship"})
    PeopleNode toNode(People people);

    @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "assigns", source = "assigns", qualifiedByName = {"resolveServiceNode", "mapToAssignment"})
    People fromNode(PeopleNode peopleNode);

    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "code", source = "skillNode.code")
    @Mapping(target = "id", expression = "java(certificateRelationship.id().value())")
    Certificate certificateRelationshipToCertificate(CertificateRelationship certificateRelationship);

    @Mapping(target = "skillNode", source = "code", qualifiedByName = {"resolveServiceNode", "resolveCodeToSkillNode"})
    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    CertificateRelationship certificateToCertificateRelationship(Certificate certificate);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "id", expression = "java(role.id().value())")
    com.sngular.skilltree.model.Role roleToRole(Role role);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    Role roleToRole1(com.sngular.skilltree.model.Role role);

    @Mapping(target = "code", source = "skillNode.code")
    @Mapping(target = "id", expression = "java(knowsRelationship.id().value())")
    Knows knowsRelationshipToKnows(KnowsRelationship knowsRelationship);

    @Mapping(target = "id", expression = "java(id().value())")
    List<Knows> knowsRelationshipListToKnowsList(List<KnowsRelationship> list);

    @Mapping(target = "skillNode", source = "code", qualifiedByName={"resolveServiceNode", "resolveCodeToSkillNode"})
    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    KnowsRelationship knowsToKnowsRelationship(Knows knows);

    List<People> map(List<PeopleNode> all);
}
