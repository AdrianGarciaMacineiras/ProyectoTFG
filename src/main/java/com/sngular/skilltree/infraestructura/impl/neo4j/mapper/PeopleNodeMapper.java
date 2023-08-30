package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CertificateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.KnowsRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.Role;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PeopleExtendedView;
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
    //@Mapping(target = "birthDate", expression = "java(toLocalDateTime(people.birthDate()))")
    @Mapping(target = "assigns", source = "assigns", qualifiedByName = {"resolveServiceNode", "mapToAssignedRelationship"})
        //@Mapping(target = "noClients", source = "noClients", qualifiedByName = {"resolveServiceNode", "mapToClientNode"})
        //@Mapping(target = "noProjects", source = "noProjects", qualifiedByName = {"resolveServiceNode", "mapToProjectNode"})
    PeopleNode toNode(People people);

    //@Mapping(target = "birthDate", expression = "java(toLocalDate(peopleNode.getBirthDate()))")
    @Mapping(target = "assigns", source = "assigns", qualifiedByName = {"resolveServiceNode", "mapToAssignment"})
        //@Mapping(target = "noClients", source = "noClients", qualifiedByName = {"resolveServiceNode", "mapToClientString"})
        //@Mapping(target = "noProjects", source = "noProjects", qualifiedByName = {"resolveServiceNode", "mapToProjectString"})
    People fromNode(PeopleNode peopleNode);

    @Mapping(target = "workWith", source = "workWith")
    People fromView(PeopleView peopleView);

    @Mapping(target = "date", expression = "java(toLocalDate(certificateRelationship.date()))")
    @Mapping(target = "code", source = "skillNode.code")
    @Mapping(target = "name", source = "skillNode.name")
    Certificate certificateRelationshipToCertificate(CertificateRelationship certificateRelationship);

    @Mapping(target = "skillNode", source = "code", qualifiedByName = {"resolveServiceNode", "resolveCodeToSkillNode"})
    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    @Mapping(target = "date", expression = "java(toLocalDateTime(certificate.date()))")
    CertificateRelationship certificateToCertificateRelationship(Certificate certificate);

    @Mapping(target = "initDate", dateFormat = "dd-MM-yyyy")
    com.sngular.skilltree.model.Role roleToRole(Role role);

    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    Role roleToRole1(com.sngular.skilltree.model.Role role);

    @Mapping(target = "code", source = "skill.code")
    @Mapping(target = "name", source = "skill.name")
    Knows knowsRelationshipToKnows(KnowsRelationship knowsRelationship);

    @Mapping(target = "code", source = "skill.code")
    @Mapping(target = "name", source = "skill.name")
    Knows knowsViewToKnows(KnowsView knowsView);

    List<Knows> knowsRelationshipListToKnowsList(List<KnowsRelationship> list);

    List<Certificate> certificateViewListToCertificateList(List<PeopleView.CertificateView> certificateViewList);

    @Mapping(target = "code", source = "skillNode.code")
    @Mapping(target = "name", source = "skillNode.name")
    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    Certificate certificateViewToCertificate(PeopleView.CertificateView certificateView);

    @Mapping(target = "skill", source = "code", qualifiedByName = {"resolveServiceNode", "resolveCodeToSkillNode"})
    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    KnowsRelationship knowsToKnowsRelationship(Knows knows);

    List<People> map(List<PeopleView> all);

    List<com.sngular.skilltree.model.views.PeopleView> mapExtended(List<PeopleExtendedView> all);

    List<Knows> mapKnows(List<PeopleExtendedView.KnowsView> all);

    @Mapping(target = "code", source = "code")
    @Mapping(target = "name", source = "name")
    Skill map(PeopleView.SkillView skillView);

    default LocalDateTime toLocalDateTime(LocalDate date) {
        return date.atStartOfDay();
    }

    default LocalDate toLocalDate(LocalDateTime dateTime) {
        return dateTime != null ? dateTime.toLocalDate() : null;
    }
}
