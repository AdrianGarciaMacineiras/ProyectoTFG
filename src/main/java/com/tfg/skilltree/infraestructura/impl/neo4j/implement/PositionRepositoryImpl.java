package com.tfg.skilltree.infraestructura.impl.neo4j.implement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.tfg.skilltree.infraestructura.PositionRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.PositionCrudRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.customrepository.CustomPositionRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.mapper.PositionNodeMapper;
import com.tfg.skilltree.model.People;
import com.tfg.skilltree.model.Position;
import com.tfg.skilltree.model.PositionAssignment;
import com.tfg.skilltree.model.views.PositionView;
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

      var queryCreatePosition = String.format("CREATE(p:Position{code:'%s', name:'%s', deleted:false, initDate:datetime('%s')" +
                        ", endDate:datetime('%s'), priority:'%s', mode:'%s', charge:'%s', active: %s})", position.code(),
                        position.name(), position.openingDate().atStartOfDay(), position.closingDate().atStartOfDay(),
                        position.priority(), position.mode(), position.role(), position.active().equalsIgnoreCase("true"));

      client.query(queryCreatePosition).run();

      var queryCreateProjectRelationship = String.format("MATCH(p:Position), (n:Project)" +
                        "WHERE p.code = '%s' AND n.code='%s'" +
                        "CREATE (p)-[:FOR_PROJECT]->(n)",
                        position.code(), position.project().code());

      client.query(queryCreateProjectRelationship).run();

      var queryCreateManagedByRelationship = String.format("MATCH(p:People), (n:Position)" +
                        "WHERE p.code='%s' AND n.code='%s'" +
                        "CREATE(p)-[m:MANAGED]->(n)",
                        position.managedBy().code(), position.code());

      client.query(queryCreateManagedByRelationship).run();

      for (var skill : position.skills()){
          var queryCreateNeedSkillRelationship = String.format("MATCH(p:Position), (s:Skill)" +
                            "WHERE p.code='%s' AND s.code='%s'" +
                            "CREATE(p)-[n:NEED{req_level:'%s'," +
                            "min_level:'%s',min_exp:%d}]->(s)",position.code(), skill.skill().getCode(), skill.levelReq(),
                            skill.minLevel(), skill.minExp());

          client.query(queryCreateNeedSkillRelationship).run();
      }
      var aux = crud.getByCode(position.code());
      return mapper.fromNode(aux);
  }

  @Override
  public boolean existByCode(String positionCode) {
    return crud.existsByCodeAndDeletedFalse(positionCode);
  }
  @Override
  public Position findByCode(String positionCode) {
      var position =crud.getByCode(positionCode);
      if(Objects.isNull(position))
          return null;
      else
        return mapper.fromNode(position);
  }

    @Override
    public boolean deleteByCode(String positionCode) {
        var node = crud.getByCode(positionCode);
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
            .map(crud::getByCode)
            .map(mapper::fromNode)
            .toList();
  }

  @Override
  public List<PositionAssignment> getPeopleAssignedToPosition(String positionCode) {
    var query = String.format("MATCH(p:People)-[r:COVER]-(s:Position{code:'%s'}) RETURN p,r", positionCode);

    var aux = new ArrayList<>(client
                             .query(query)
                             .fetchAs(PositionAssignment.class)
                             .mappedBy((TypeSystem t, Record people) -> {

                               People person = getPeople(people);

                               var assign = people.get("r");

                                 assign.get("dedication").asInt();
                                 return PositionAssignment.builder()
                                                        .assignDate(assign.get("assignDate").asLocalDate())
                                                        .id(assign.get("id").asString())
                                                        .assigned(person)
                                                        .role(assign.get("role").asString())
                                                        .dedication(assign.get("dedication").asInt())
                                                        .build();

                             })
            .all());
    return aux;
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