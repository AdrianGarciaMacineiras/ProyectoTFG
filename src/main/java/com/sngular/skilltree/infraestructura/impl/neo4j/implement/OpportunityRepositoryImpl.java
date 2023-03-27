package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import static com.sngular.skilltree.model.EnumLevelReq.MANDATORY;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.model.*;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.OpportunityNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.PeopleNode;

import java.time.LocalDate;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpportunityRepositoryImpl implements OpportunityRepository {

  private final List<String> LOW_LEVEL_LIST = List.of("LOW", "MEDIUM", "HIGH");
  private final List<String> MID_LEVEL_LIST = List.of( "MEDIUM", "HIGH");
  private final List<String> HIGH_LEVEL_LIST = List.of("HIGH");

  private final OpportunityCrudRepository crud;

  private final PeopleCrudRepository peopleCrud;

  private final ProjectCrudRepository projectCrud;

  private final ClientCrudRepository clientCrud;

  private final OfficeCrudRepository officeCrud;

  private final PeopleNodeMapper peopleNodeMapper;

  private final OpportunityNodeMapper mapper;

  private final Neo4jClient client;

  @Override
  public List<Opportunity> findAll() {
    return mapper.map(crud.findByDeletedIsFalse());
  }

  @Override
  public Opportunity save(Opportunity opportunity) {

    var opportunityNode = crud.findByCode(opportunity.code());
    if (Objects.isNull(opportunityNode.getClient()) || opportunityNode.getClient().isDeleted()) {
      throw new EntityNotFoundException("Client", opportunityNode.getClient().getCode());
    }

    var projectNode = projectCrud.findByCode(opportunity.project().code());
    if (Objects.isNull(projectNode) || projectNode.isDeleted()) {
      throw new EntityNotFoundException("Project", projectNode.getCode());
    }

    var officeNode = officeCrud.findByCode(opportunity.office().code());
    if (Objects.isNull(officeNode) || officeNode.isDeleted()) {
      throw new EntityNotFoundException("Office", officeNode.getCode());
    }

    final var filter = new StringBuilder();
    for (var opportunitySkill : opportunity.skills()){
      if (MANDATORY.equals(opportunitySkill.levelReq())) {
        switch (opportunitySkill.minLevel()) {
          case LOW -> fillFilterBuilder(filter, opportunitySkill.skill().code(), LOW_LEVEL_LIST);
          case MEDIUM -> fillFilterBuilder(filter, opportunitySkill.skill().code(), MID_LEVEL_LIST);
          default -> fillFilterBuilder(filter, opportunitySkill.skill().code(),  HIGH_LEVEL_LIST);
        }
      }
    }
    /**
     * MATCH (p:People)-[r:KNOWS]->(s:Skill)
     * WHERE s.code IN ['code1', 'code2', 'code3', ..., 'codeN']
     * WITH p, COLLECT(DISTINCT {code:s.code, level:r.level}) AS skills
     * WHERE ALL(skill IN [{code:'code1', level:1}, {code:'code2', level:2}, {code:'code3', level:3}, ..., {code:'codeN', level:N}]
     *       WHERE skill IN skills)
     * RETURN p
     */

    /**
     *MATCH (p:People)-[r:knows]->(s:Skill)
     * WHERE ALL(pair IN [{skillcode:'code1', knowslevel:['level1','level2','level3']},{skillcode:'code2', knowslevel:['level4']},....,{skillcode:'codeN', knowslevel:['level5',
     'level6']}]
     *           WHERE pair.skillcode = s.Code AND pair.knowslevel = r.Level)
     * RETURN p
     */

    var query = String.format("MATCH (p:People)-[r:KNOWS]->(s:Skill) WHERE ALL(pair IN [%s] " +
                " WHERE (p)-[:KNOWS]->(:Skill {code: pair.skillcode}) AND ANY(lvl IN pair.knowslevel WHERE (p)-[:KNOWS {level: lvl}]->(:Skill {code: pair.skillcode})))" +
                " RETURN DISTINCT p", filter);

    var peopleList = client.query(query).fetchAs(People.class)
                           .mappedBy((TypeSystem t, Record record) -> {

                             People people = People.builder()
                                                   .name(record.get("p").get("name").asString())
                                                   .surname(record.get("p").get("surname").asString())
                                                   .employeeId(record.get("p").get("employeeId").asString())
                                                   .birthDate(record.get("p").get("birthDate").asLocalDate())
                                                   .code(record.get("p").get("code").asLong())
                                                   .deleted(record.get("p").get("deleted").asBoolean())
                                                   .participate(new ArrayList<>())
                                                   .build();

                             return people;

                           })
                           .all();

    Map<Long, People> knowsMap = new HashMap<>();

    peopleList.forEach(people ->
                         knowsMap.compute(people.code(), (code, aggPeople) -> {
                           if(Objects.isNull(aggPeople)){
                             return people;
                           } else {
                             var knowList = new ArrayList<>(aggPeople.knows());
                             knowList.addAll(people.knows());
                             aggPeople = aggPeople.toBuilder().knows(knowList).build();
                           }
                           return aggPeople;
                         })
    );  //  codeList.addAll(peopleCrud.findCandidatesSkillList(opportunitySkill.skill().code(), levelList));

  /*  //Limpio los repetidos de la lista
    Set<Long> set = new HashSet<>(codeList);
    codeList.clear();
    codeList.addAll(set);

    for (var code : codeList) {
      //Busco la persona para poder recorrer por la relacion de Knows
      var peopleNode = peopleCrud.findByCode(code);
      for (var skill : opportunity.skills()) {
        //Filtrar por MANDATORY
        if (skill.levelReq().equals(MANDATORY)) {
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
        if (!acceptCandidate) {
          break;
        }
      }
      //Si al salir es true, es que tiene todas las skills mandatory y se mete como candidato en la lista
      if (acceptCandidate) {
        peopleNodeList.add(peopleNode);
        acceptCandidate = false;
      }
    }*/

    var candidateList = new ArrayList<Candidate>();
    for (var people : peopleList) {

      //Lo paso de peopleNode a People
      //var people = peopleNodeMapper.fromNode(peopleNode);

      //Creo el objeto candidato
      Candidate candidate = Candidate.builder()
              .code(opportunity.code()+ "-" + people.employeeId())
              .candidate(people)
              .opportunity(opportunity)
              .status(EnumStatus.ASSIGNED)
              .creationDate(LocalDate.now())
              .build();

      //Lo meto en la lista de candidatos
      candidateList.add(candidate);
    }

    //Meto la lista de candidatos en la oportunidad y la creo
    opportunity.candidates().addAll(candidateList);
    return mapper.fromNode(crud.save(mapper.toNode(opportunity)));
  }

  private void fillFilterBuilder(final StringBuilder filter, final String skillCode, final List<String> levelList) {
    filter.append(String.format("{skillcode:'%s', knowslevel:[%s]}), ", skillCode, String.join(",", levelList)));
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
    return true;
  }

  @Override
  public List<Opportunity> findByDeletedIsFalse() {
    return mapper.map(crud.findByDeletedIsFalse());
  }


}
