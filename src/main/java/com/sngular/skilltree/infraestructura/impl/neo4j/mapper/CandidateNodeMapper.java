package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CertificateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.Role;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.Certificate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ResolveServiceNode.class, OpportunityNodeMapper.class, PeopleNodeMapper.class,
        SkillNodeMapper.class})
public interface CandidateNodeMapper {

    @InheritInverseConfiguration
    CandidateNode toNode(Candidate candidate);

    Candidate fromNode(CandidateNode candidateNode);

    List<Candidate> map(List<CandidateNode> all);
}
