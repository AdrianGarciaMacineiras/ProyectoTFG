package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.CandidateNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.EnumMinLevel;
import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.OpportunityNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;

import java.util.ArrayList;
import java.util.List;

import com.sngular.skilltree.model.SkillsCandidate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpportunityRepositoryImpl implements OpportunityRepository {

  private final OpportunityCrudRepository crud;

  private final PeopleCrudRepository peopleCrud;

  private final PeopleNodeMapper peopleNodeMapper;

  private final CandidateNodeMapper candidateNodeMapper;

  private final CandidateCrudRepository candidateCrud;

  private final OpportunityNodeMapper mapper;

  @Override
  public List<Opportunity> findAll() {
    return mapper.map(crud.findByDeletedIsFalse());
  }

  @Override
  public Opportunity save(Opportunity opportunity) {
    List<PeopleNode> peopleNodeList;
    List<SkillsCandidate> skillsCandidateList = new ArrayList<>();
    List<String> skillList = new ArrayList<>();
    List<Candidate> candidateList = new ArrayList<>();
    List<String> levelList = new ArrayList<>();
    for (var opportunitySkill : opportunity.skills()){
      if (opportunitySkill.minLevel().equals(EnumMinLevel.LOW)){
        levelList.add("LOW");
        levelList.add("MEDIUM");
        levelList.add("HIGH");
      } else {
        if (opportunitySkill.minLevel().equals(EnumMinLevel.MEDIUM)){
          levelList.add("MEDIUM");
          levelList.add("HIGH");
        } else {
          levelList.add("HIGH");
        }
      }
      if(!skillList.contains(opportunitySkill.skill().code()))
        skillList.add(opportunitySkill.skill().code());
    }

    peopleNodeList = peopleCrud.findCandidatesSkillList(skillList,levelList);
    levelList.clear();
    for (var peopleNode : peopleNodeList){
        peopleNode.getKnows().clear();
        var people = peopleNodeMapper.fromNode(peopleNode);
        Candidate candidate = Candidate.builder()
              .code(opportunity.code()+ "-" + peopleNode.getEmployeeId())
              .candidate(people)
              .opportunity(opportunity)
              .build();
        candidateList.add(candidate);
        skillsCandidateList.clear();
    }
    opportunity.candidates().addAll(candidateList);
    return mapper.fromNode(crud.save(mapper.toNode(opportunity)));
  }

  @Override
  public Opportunity findByCode(String opportunitycode) {
    return mapper.fromNode(crud.findByCode(opportunitycode));
  }

  @Override
  public boolean deleteByCode(String opportunitycode) {
    var node = crud.findByCode(opportunitycode);
    node.setDeleted(true);
    crud.save(node);
    //crud.delete(node);
    return true;
  }

  @Override
  public List<Opportunity> findByDeletedIsFalse() {
    return mapper.map(crud.findByDeletedIsFalse());
  }


}
