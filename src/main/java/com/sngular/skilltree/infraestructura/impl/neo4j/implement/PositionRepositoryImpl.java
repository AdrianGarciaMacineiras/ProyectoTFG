package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.PositionRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.ClientCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.OfficeCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PositionCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.ProjectCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.customrepository.CustomPositionRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PositionNodeMapper;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.model.PositionAssignment;
import com.sngular.skilltree.model.views.PositionView;
import lombok.RequiredArgsConstructor;
import org.neo4j.driver.Record;
import org.neo4j.driver.types.TypeSystem;
import org.springframework.data.neo4j.core.Neo4jClient;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PositionRepositoryImpl implements PositionRepository {

  private final PositionCrudRepository crud;

  private final CustomPositionRepository customCrud;

  private final ProjectCrudRepository projectCrud;

  private final PositionNodeMapper mapper;

  private final Neo4jClient client;

  public static final String NULL = "null";

  @Override
  public List<Position> findAll() {
    return mapper.map(crud.findByDeletedIsFalse());
  }

  @Override
  public List<PositionView> findAllResumed() {
    var aux = customCrud.getAllPositionExtended();
    return mapper.mapExtended(aux);
  }

  @Override
  public Position save(Position position) {

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

      var query = String.format("MATCH(p:People{code:%s})-[r:COVER]-(s:Position) RETURN s.code", peopleCode);

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
    var query = String.format("MATCH(p:People)-[r:COVER]-(s:Position{code:'%s'}) RETURN p,r", positionCode);

    return new ArrayList<>(client
                             .query(query)
                             .fetchAs(PositionAssignment.class)
                             .mappedBy((TypeSystem t, Record people) -> {

                               People person = getPeople(people);

                               var assign = people.get("r");

                               return PositionAssignment.builder()
                                                        .assignDate(NULL.equalsIgnoreCase(assign.get("assignDate").asString()) ? null : assign.get("assignDate").asLocalDate())
                                                        .id(assign.get("id").asString())
                                                        .assigned(person)
                                                        .role(assign.get("role").asString())
                                                        //.dedication(Objects.isNull(assign.get("dedication").asInt()) ? null : assign.get("dedication").asInt())
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
                 //.birthDate(NULL.equalsIgnoreCase(people.get("birthDate").asString()) ? null : people.get("birthDate").asLocalDate())
                 .code(people.get("code").asString())
                 .deleted(people.get("deleted").asBoolean())
                 .build();
  }

}
