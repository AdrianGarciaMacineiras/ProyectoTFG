package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import static com.sngular.skilltree.model.EnumLevelReq.MANDATORY;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.impl.neo4j.*;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PuestoNodeMapper;
import com.sngular.skilltree.model.*;
import com.sngular.skilltree.infraestructura.PuestoRepository;

import java.time.LocalDate;
import java.util.*;

import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PuestoRepositoryImpl implements PuestoRepository {

  private final List<String> LOW_LEVEL_LIST = List.of("'LOW'", "'MEDIUM'", "'HIGH'");
  private final List<String> MID_LEVEL_LIST = List.of( "'MEDIUM'", "'HIGH'");
  private final List<String> HIGH_LEVEL_LIST = List.of("'HIGH'");

  private final PuestoCrudRepository crud;

  private final PeopleCrudRepository peopleCrud;

  private final ProjectCrudRepository projectCrud;

  private final ClientCrudRepository clientCrud;

  private final OfficeCrudRepository officeCrud;

  private final PeopleNodeMapper peopleNodeMapper;

  private final PuestoNodeMapper mapper;

  private final Neo4jClient client;

  @Override
  public List<Puesto> findAll() {
    return mapper.map(crud.findByDeletedIsFalse());
  }

  @Override
  public Puesto save(Puesto puesto) {

    var clientNode = clientCrud.findByCode(puesto.client().code());
    if (Objects.isNull(clientNode) || clientNode.isDeleted()) {
      throw new EntityNotFoundException("Client", clientNode.getCode());
    }

    var projectNode = projectCrud.findByCode(puesto.project().code());
    if (Objects.isNull(projectNode) || projectNode.isDeleted()) {
      throw new EntityNotFoundException("Project", projectNode.getCode());
    }

    var officeNode = officeCrud.findByCode(puesto.office().code());
    if (Objects.isNull(officeNode) || officeNode.isDeleted()) {
      throw new EntityNotFoundException("Office", officeNode.getCode());
    }

    final var filter = new ArrayList<String>();
    for (var puestoSkill : puesto.skills()){
      if (MANDATORY.equals(puestoSkill.levelReq())) {
        switch (puestoSkill.minLevel()) {
          case LOW -> filter.add(fillFilterBuilder(puestoSkill.skill().code(), LOW_LEVEL_LIST));
          case MEDIUM -> filter.add(fillFilterBuilder(puestoSkill.skill().code(), MID_LEVEL_LIST));
          default -> filter.add(fillFilterBuilder(puestoSkill.skill().code(), HIGH_LEVEL_LIST));
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
              .code(people.code()+ "-" + people.employeeId())
              .candidate(people)
              .puesto(puesto)
              .status(EnumStatus.ASSIGNED)
              .creationDate(LocalDate.now())
              .build();

      candidateList.add(candidate);
    }

    puesto.candidates().addAll(candidateList);
    return mapper.fromNode(crud.save(mapper.toNode(puesto)));
  }

  private String fillFilterBuilder(final String skillCode, final List<String> levelList) {
    return String.format("{skillcode:'%s', knowslevel:[%s]}", skillCode, String.join(",", levelList));
  }

  @Override
  public Puesto findByCode(String opportunitycode) {
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
  public List<Puesto> findByDeletedIsFalse() {
    return mapper.map(crud.findByDeletedIsFalse());
  }


}
