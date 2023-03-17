package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.customRepository.CandidateRelationshipProjection;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import com.sngular.skilltree.model.Candidate;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {ResolveServiceNode.class, OpportunityNodeMapper.class, PeopleNodeMapper.class,
        SkillNodeMapper.class})
public interface CandidateNodeMapper {

    @InheritInverseConfiguration
    CandidateRelationship toNode(Candidate candidate);

    Candidate fromNode(CandidateRelationship candidateRelationship);

    List<Candidate> map(List<CandidateRelationship> all);

    List<Candidate> mapProjection(List<CandidateRelationshipProjection> all);

    Candidate toCandidate(CandidateRelationshipProjection candidateRelationshipProjection);

}
