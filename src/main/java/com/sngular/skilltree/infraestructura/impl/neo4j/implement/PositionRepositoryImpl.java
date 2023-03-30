package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import static com.sngular.skilltree.model.EnumLevelReq.MANDATORY;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.impl.neo4j.*;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PositionNodeMapper;
import com.sngular.skilltree.model.*;
import com.sngular.skilltree.infraestructura.PositionRepository;

import java.time.LocalDate;
import java.util.*;
import java.util.function.BiFunction;

import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PositionRepositoryImpl implements PositionRepository {

  private final List<String> LOW_LEVEL_LIST = List.of("'LOW'", "'MEDIUM'", "'HIGH'");
  private final List<String> MID_LEVEL_LIST = List.of( "'MEDIUM'", "'HIGH'");
  private final List<String> HIGH_LEVEL_LIST = List.of("'HIGH'");

  private final PositionCrudRepository crud;

  private final ProjectCrudRepository projectCrud;

  private final ClientCrudRepository clientCrud;

  private final OfficeCrudRepository officeCrud;

  private final PositionNodeMapper mapper;

  private final Neo4jClient client;

  @Override
  public List<Position> findAll() {
    return mapper.map(crud.findByDeletedIsFalse());
  }

  @Override
  public Position save(Position position) {

    var clientNode = clientCrud.findByCode(position.client().code());
    if (Objects.isNull(clientNode) || clientNode.isDeleted()) {
      throw new EntityNotFoundException("Client", clientNode.getCode());
    }

    var projectNode = projectCrud.findByCode(position.project().code());
    if (Objects.isNull(projectNode) || projectNode.isDeleted()) {
      throw new EntityNotFoundException("Project", projectNode.getCode());
    }

    var officeNode = officeCrud.findByCode(position.office().code());
    if (Objects.isNull(officeNode) || officeNode.isDeleted()) {
      throw new EntityNotFoundException("Office", officeNode.getCode());
    }

    final var filter = new ArrayList<String>();
    for (var positionSkill : position.skills()){
      if (MANDATORY.equals(positionSkill.levelReq())) {
        switch (positionSkill.minLevel()) {
          case LOW -> filter.add(fillFilterBuilder(positionSkill.skill().code(), LOW_LEVEL_LIST));
          case MEDIUM -> filter.add(fillFilterBuilder(positionSkill.skill().code(), MID_LEVEL_LIST));
          default -> filter.add(fillFilterBuilder(positionSkill.skill().code(), HIGH_LEVEL_LIST));
        }
      }
    }

    var query = String.format("MATCH (p:People)-[r:KNOWS]->(s:Skill) WHERE ALL(pair IN [%s] " +
            " WHERE (p)-[:KNOWS]->(:Skill {code: pair.skillcode}) AND ANY(lvl IN pair.knowslevel WHERE (p)-[:KNOWS {level: lvl}]->(:Skill {code: pair" +
            ".skillcode})))" +
            " RETURN DISTINCT p", String.join(",", filter));

    var peopleList = client.query(query).fetchAs(People.class)
            .mappedBy(getTypeSystemRecordPeopleBiFunction())
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
              .position(position)
              .status(EnumStatus.ASSIGNED)
              .creationDate(LocalDate.now())
              .build();

      candidateList.add(candidate);
    }

    position.candidates().addAll(candidateList);
    return mapper.fromNode(crud.save(mapper.toNode(position)));
  }

  private static BiFunction<TypeSystem, Record, People> getTypeSystemRecordPeopleBiFunction() {
    return (TypeSystem t, Record record) ->
       People.builder()
              .name(record.get("p").get("name").asString())
              .surname(record.get("p").get("surname").asString())
              .employeeId(record.get("p").get("employeeId").asString())
              .birthDate(record.get("p").get("birthDate").asLocalDate())
              .code(record.get("p").get("code").asLong())
              .deleted(record.get("p").get("deleted").asBoolean())
              .build();
  }

  private String fillFilterBuilder(final String skillCode, final List<String> levelList) {
    return String.format("{skillcode:'%s', knowslevel:[%s]}", skillCode, String.join(",", levelList));
  }

  @Override
  public Position findByCode(String positioncode) {
    return mapper.fromNode(crud.findByCode(positioncode));
  }

  @Override
  public boolean deleteByCode(String positioncode) {
    var node = crud.findByCode(positioncode);
    node.setDeleted(true);
    crud.save(node);
    return true;
  }

  @Override
  public List<Position> findByDeletedIsFalse() {
    return mapper.map(crud.findByDeletedIsFalse());
  }


}
