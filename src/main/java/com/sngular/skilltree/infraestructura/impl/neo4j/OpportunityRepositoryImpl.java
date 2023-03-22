package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.model.*;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.OpportunityNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpportunityRepositoryImpl implements OpportunityRepository {

  private final OpportunityCrudRepository crud;

  private final PeopleCrudRepository peopleCrud;

  private final PeopleNodeMapper peopleNodeMapper;

  private final OpportunityNodeMapper mapper;

  private final Neo4jClient client;


  @Override
  public List<Opportunity> findAll() {
    return mapper.map(crud.findByDeletedIsFalse());
  }

  @Override
  public Opportunity save(Opportunity opportunity) {
    List<PeopleNode> peopleNodeList = new ArrayList<>();
    List<Long> codeList = new ArrayList<>();
    boolean acceptCandidate = false;
    List<SkillsCandidate> skillsCandidateList = new ArrayList<>();
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

      //Busco aquellos People que tienen conocimientos en la skill y filtro por el nivel, pero solo recupero su codigo
      codeList.addAll(peopleCrud.findCandidatesSkillList(opportunitySkill.skill().code(), levelList));
      levelList.clear();

//      if(!skillList.contains(opportunitySkill.skill().code()))
//        skillList.add(opportunitySkill.skill().code());
    }
    //Limpio los repetidos de la lista
    Set<Long> set = new HashSet<>(codeList);
    codeList.clear();
    codeList.addAll(set);

    for (var code : codeList) {
      //Busco la persona para poder recorrer por la relacion de Knows
      var peopleNode = peopleCrud.findByCode(code);
      for (var skill : opportunity.skills()) {
        //Filtrar por MANDATORY
        if (skill.levelReq().equals(EnumLevelReq.MANDATORY)) {
          for (var know : peopleNode.getKnows()) {
            if (know.skillNode().getCode().equalsIgnoreCase(skill.skill().code())) {
              //Si tiene conocimiento de la Skill, no hace falta mirar mas
              acceptCandidate = true;
              break;
            } else {
              acceptCandidate = false;
            }
          }
        }
        //Si tras mirar por los Knows del peoplenode no tiene conocimiento de dicha skill se sale y ya no es apto
        // para ser candidato
        if (!acceptCandidate)
          break;
      }
      //Si al salir es true, es que tiene todas las skills mandatory y se mete como candidato en la lista
      if (acceptCandidate) {
        peopleNodeList.add(peopleNode);
        acceptCandidate = false;
      }
    }

    for (var peopleNode : peopleNodeList) {
      //Vacio las relaciones para que no se me dupliquen
      peopleNode.getKnows().clear();
      peopleNode.getParticipate().clear();
      peopleNode.getCertificates().clear();
      peopleNode.getMaster().clear();
      peopleNode.getInterest().clear();
      peopleNode.getWork_with().clear();
      //Lo paso de peopleNode a People
      var people = peopleNodeMapper.fromNode(peopleNode);
      //Creo el objeto candidato, me falta saber que meter en los campos:
      //  - status
      //  - introductionDate
      //  - resulutionDate
      Candidate candidate = Candidate.builder()
              .code(opportunity.code()+ "-" + peopleNode.getEmployeeId())
              .candidate(people)
              .opportunity(opportunity)
              .build();
      //Lo meto en la lista de candidatos
      candidateList.add(candidate);
        //skillsCandidateList.clear();
    }
    //Meto la lista de candidatos en la oportunidad y la creo
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
