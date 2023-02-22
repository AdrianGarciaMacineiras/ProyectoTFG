package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CertificateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ParticipateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;
import com.sngular.skilltree.model.Certificate;
import com.sngular.skilltree.model.Participate;
import com.sngular.skilltree.model.People;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = ResolveRepository.class)
public interface PeopleNodeMapper {

    @InheritInverseConfiguration
    @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
    PeopleNode toNode(People People);

    @Mapping(target = "birthDate", dateFormat = "dd-MM-yyyy")
    People fromNode(PeopleNode peopleNode);

    @Mapping(target = "date", dateFormat = "dd-MM-yyyy")
    Certificate certificateRelationshipToCertificate(CertificateRelationship certificateRelationship);

    /*@Mapping(target = "name", source = "project.code")
    @Mapping(target = "roles", qualifiedByName = {"resolveRepository", "resolveRoles"})
    Participate participateRelationshipToParticipate(ParticipateRelationship participateRelationship);*/

    List<People> map(List<PeopleNode> all);
}
