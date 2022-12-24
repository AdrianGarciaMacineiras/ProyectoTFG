package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateNode;
import com.sngular.skilltree.model.Candidate;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CandidateNodeMapper {
    @InheritInverseConfiguration
    CandidateNode toNode(Candidate candidate);

    Candidate fromNode(CandidateNode candidateNode);

    List<Candidate> map(List<CandidateNode> all);
}
