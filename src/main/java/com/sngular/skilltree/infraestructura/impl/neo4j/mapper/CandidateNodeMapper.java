package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.KnowsRelationship;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.Knows;
import org.mapstruct.*;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring", uses = {ResolveServiceNode.class, PositionNodeMapper.class, PeopleNodeMapper.class,
        SkillNodeMapper.class})
public interface CandidateNodeMapper {

    @InheritInverseConfiguration
    CandidateRelationship toNode(Candidate candidate);

    @Mapping(target = "creationDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "introductionDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "resolutionDate", dateFormat = "dd-MM-yyyy")
    Candidate fromNode(CandidateRelationship candidateRelationship);

    List<Candidate> map(List<CandidateRelationship> all);

}
