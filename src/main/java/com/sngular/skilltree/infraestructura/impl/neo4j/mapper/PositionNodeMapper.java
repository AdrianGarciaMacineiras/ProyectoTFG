package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.ResolveServiceNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PositionNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PositionSkillsRelationship;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PositionExtendedView;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.model.PositionSkill;
import com.sngular.skilltree.model.views.PositionView;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = CommonMapperConfiguration.class, uses = {SkillNodeMapper.class, PeopleNodeMapper.class, OfficeNodeMapper.class,
  ProjectNodeMapper.class, CandidateNodeMapper.class, ResolveServiceNode.class})
public interface PositionNodeMapper {

  @InheritInverseConfiguration(name="fromNode")
  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  //@Mapping(target = "assigns", source = "assignedPeople", qualifiedByName = {"resolveServiceNode", "assignedRelationshipToPositionAssignment"})
  PositionNode toNode(Position position);

  @Mapping(target = "openingDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "closingDate", dateFormat = "dd-MM-yyyy")
  //@Mapping(target = "assignedPeople", source = "assigns", qualifiedByName = {"resolveServiceNode", "positionAssignmentToAssignedRelationship"})
  Position fromNode(PositionNode positionNode);

  @Mapping(target = "min_exp", source = "minExp")
  @Mapping(target = "min_level", source = "minLevel")
  @Mapping(target = "req_level", source = "levelReq")
  @Mapping(target = "id", source = "id", qualifiedByName = {"resolveServiceNode", "resolveId"})
  PositionSkillsRelationship toPositionSkillsRelationship(PositionSkill positionSkill);

  @InheritInverseConfiguration
  PositionSkill toPositionSkill(PositionSkillsRelationship positionSkillsRelationship);

  List<Position> map(List<PositionNode> all);

  List<PositionView> mapExtended(List<PositionExtendedView> all);

  @Mapping(target = "managedBy", source = "managedBy", qualifiedByName = {"resolveServiceNode", "mapToPeople"})
  PositionView mapToPositionView(PositionExtendedView positionExtendedView);

  List<Candidate> mapCandidates(List<PositionExtendedView.CandidateView> all);

  @Mapping(target = "candidate", source = "candidateCode", qualifiedByName = {"resolveServiceNode", "mapToPeople"})
  @Mapping(target = "introductionDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "resolutionDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "creationDate", dateFormat = "dd-MM-yyyy")
  @Mapping(target = "interviewDate", dateFormat = "dd-MM-yyyy")
  Candidate candidateViewToCandidate(PositionExtendedView.CandidateView candidateView);
}
