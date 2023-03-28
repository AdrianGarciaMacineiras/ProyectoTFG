package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import static com.sngular.skilltree.model.EnumLevelReq.MANDATORY;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.OpportunityRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.ClientCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.OfficeCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.OpportunityCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.ProjectCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.OpportunityNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.EnumStatus;
import com.sngular.skilltree.model.Opportunity;
import com.sngular.skilltree.model.People;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OpportunityRepositoryImpl implements OpportunityRepository {

  private final static List<String> LOW_LEVEL_LIST = List.of("'LOW'", "'MEDIUM'", "'HIGH'");

  private final static List<String> MID_LEVEL_LIST = List.of("'MEDIUM'", "'HIGH'");

  private final static List<String> HIGH_LEVEL_LIST = List.of("'HIGH'");

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

    var clientNode = clientCrud.findByCode(opportunity.client().code());
    if (Objects.isNull(clientNode) || clientNode.isDeleted()) {
      throw new EntityNotFoundException("Client", clientNode.getCode());
    }

    var projectNode = projectCrud.findByCode(opportunity.project().code());
    if (Objects.isNull(projectNode) || projectNode.isDeleted()) {
      throw new EntityNotFoundException("Project", projectNode.getCode());
    }

    var officeNode = officeCrud.findByCode(opportunity.office().code());
    if (Objects.isNull(officeNode) || officeNode.isDeleted()) {
      throw new EntityNotFoundException("Office", officeNode.getCode());
    }

    final var filter = new ArrayList<String>();
    for (var opportunitySkill : opportunity.skills()){
      if (MANDATORY.equals(opportunitySkill.levelReq())) {
        switch (opportunitySkill.minLevel()) {
          case LOW -> filter.add(fillFilterBuilder(opportunitySkill.skill().code(), LOW_LEVEL_LIST));
          case MEDIUM -> filter.add(fillFilterBuilder(opportunitySkill.skill().code(), MID_LEVEL_LIST));
          default -> filter.add(fillFilterBuilder(opportunitySkill.skill().code(), HIGH_LEVEL_LIST));
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
                              " WHERE (p)-[:KNOWS]->(:Skill {code: pair.skillcode}) AND ANY(lvl IN pair.knowslevel WHERE (p)-[:KNOWS {level: lvl}]->(:Skill {code: pair" +
                              ".skillcode})))" +
                              " RETURN DISTINCT p", String.join(",", filter));

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
    );

    var candidateList = new ArrayList<Candidate>();
    for (var people : peopleList) {

      Candidate candidate = Candidate.builder()
                                     .code(opportunity.code()+ "-" + people.employeeId())
                                     .candidate(people)
                                     .opportunity(opportunity)
                                     .status(EnumStatus.ASSIGNED)
                                     .creationDate(LocalDate.now())
                                     .build();

      candidateList.add(candidate);
    }

    opportunity.candidates().addAll(candidateList);
    return mapper.fromNode(crud.save(mapper.toNode(opportunity)));
  }

  private String fillFilterBuilder(final String skillCode, final List<String> levelList) {
    return String.format("{skillcode:'%s', knowslevel:[%s]}", skillCode, String.join(",", levelList));
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
