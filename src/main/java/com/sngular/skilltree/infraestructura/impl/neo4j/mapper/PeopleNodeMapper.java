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
    Certificate certificateRelationshipToCertificate(CertificateRelationship certificateRelationship);

    @Mapping(target = "skillNode", source = "code", qualifiedByName = {"resolveServiceNode", "resolveCodeToSkillNode"})
    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    CertificateRelationship certificateToCertificateRelationship(Certificate certificate);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    com.sngular.skilltree.model.Role roleToRole(Role role);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    Role roleToRole1(com.sngular.skilltree.model.Role role);

    @Mapping(target = "code", source = "skillNode.code")
    Knows knowsRelationshipToKnows(KnowsRelationship knowsRelationship);

    List<Knows> knowsRelationshipListToKnowsList(List<KnowsRelationship> list);

    @Mapping(target = "skillNode", source = "code", qualifiedByName={"resolveServiceNode", "resolveCodeToSkillNode"})
    KnowsRelationship knowsToKnowsRelationship(Knows knows);

    List<People> map(List<PeopleNode> all);
}
