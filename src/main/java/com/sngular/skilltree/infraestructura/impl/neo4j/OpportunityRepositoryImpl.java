package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.CandidateNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.CandidateNode;
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
    var opportunityNode = crud.save(mapper.toNode(opportunity));
    List<PeopleNode> peopleNodeList = new ArrayList<>();
    List<SkillsCandidate> skillsCandidateList = new ArrayList<>();
    List<String> skillList = new ArrayList<>();
    for (var opportunitySkill : opportunity.skills()){
      if(!skillList.contains(opportunitySkill.skill().code()))
        skillList.add(opportunitySkill.skill().code());
    }

    peopleNodeList = peopleCrud.findCandidatesSkillList(skillList);

    for (var peopleNode : peopleNodeList){
        for (var knows : peopleNode.getKnows()){
          SkillsCandidate skillsCandidate = SkillsCandidate.builder()
                  .level(knows.level())
                  .experience(knows.experience())
                  .code(knows.skillNode().getCode())
                  .build();
          skillsCandidateList.add(skillsCandidate);
        }
        peopleNode.getKnows().clear();
        var people = peopleNodeMapper.fromNode(peopleNode);
        Candidate candidate = Candidate.builder()
              .code(opportunityNode.getCode()+ "-" + peopleNode.getEmployeeId())
              .candidate(people)
              .opportunity(mapper.fromNode(crud.findOpportunity(opportunity.code())))
              .deleted(false)
              .skills(skillsCandidateList)
              .build();
        candidateCrud.save(candidateNodeMapper.toNode(candidate));
        skillsCandidateList.clear();
    }
    return mapper.fromNode(opportunityNode);
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
