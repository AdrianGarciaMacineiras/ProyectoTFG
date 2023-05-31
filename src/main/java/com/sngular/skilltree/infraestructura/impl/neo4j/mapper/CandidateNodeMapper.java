package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import com.sngular.skilltree.model.Candidate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = {ResolveServiceNode.class, PositionNodeMapper.class, PeopleNodeMapper.class,
  SkillNodeMapper.class})
public interface CandidateNodeMapper {

    @InheritInverseConfiguration
    @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
    CandidateRelationship toNode(Candidate candidate);

    @Mapping(target = "creationDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "introductionDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "resolutionDate", dateFormat = "dd-MM-yyyy")
    Candidate fromNode(CandidateRelationship candidateRelationship);

    List<Candidate> map(List<CandidateRelationship> all);

}
