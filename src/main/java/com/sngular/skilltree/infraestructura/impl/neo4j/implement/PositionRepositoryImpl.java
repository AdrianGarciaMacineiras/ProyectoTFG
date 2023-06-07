package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import static com.sngular.skilltree.model.EnumLevelReq.MANDATORY;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.CandidateRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.*;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
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

  private final PositionCrudRepository crud;

  private final ProjectCrudRepository projectCrud;

  private final ClientCrudRepository clientCrud;

  private final OfficeCrudRepository officeCrud;

  private final PositionNodeMapper mapper;

  private final Neo4jClient client;

  public static final String NULL = "null";

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

    var positionNode = crud.save(mapper.toNode(position));

    return mapper.fromNode(positionNode);
  }

  @Override
  public Position findByCode(String positionCode) {
      return mapper.fromNode(crud.findByCode(positionCode));
  }

    @Override
    public boolean deleteByCode(String positionCode) {
        var node = crud.findByCode(positionCode);
        node.setDeleted(true);
        crud.save(node);
        return true;
    }

  @Override
  public List<Position> findByDeletedIsFalse() {
    return mapper.map(crud.findByDeletedIsFalse());
  }

    @Override
    public List<Position> getPeopleAssignedPositions(String peopleCode) {

        var query = String.format("MATCH(p:People{code:%d})-[r:ASSIGN]-(s:Position) RETURN s.code", peopleCode);

        var positionCodes = client
                .query(query)
                .fetchAs(String.class)
                .all();

        return positionCodes
                .parallelStream()
            .map(crud::findByCode)
            .map(mapper::fromNode)
            .toList();
  }

  @Override
  public List<PositionAssignment> getPeopleAssignedToPosition(String positionCode) {
    var query = String.format("MATCH(p:People)-[r:ASSIGN]-(s:Position{code:'%s'}) RETURN p,r",positionCode);

    return new ArrayList<>(client
            .query(query)
            .fetchAs(PositionAssignment.class)
            .mappedBy((TypeSystem t, Record record) -> {

              People person = getPeople(record);

              var assign = record.get("r");

              return  PositionAssignment.builder()
                      .assignDate(assign.get("assignDate").asLocalDate())
                      .id(assign.get("id").asString())
                      .assigned(person)
                      .role(assign.get("role").asString())
                      .dedication(assign.get("dedication").asInt())
                      .endDate(NULL.equalsIgnoreCase(assign.get("endDate").asString()) ? null : assign.get("endDate").asLocalDate())
                      .build();

            })
            .all());

  }

  private static People getPeople(Record result) {
    var people = result.get("p");
    return People.builder()
            .name(people.get("name").asString())
            .surname(people.get("surname").asString())
            .employeeId(people.get("employeeId").asString())
            .birthDate(people.get("birthDate").asLocalDate())
            .code(people.get("code").asString())
            .deleted(people.get("deleted").asBoolean())
            .build();
  }

}
