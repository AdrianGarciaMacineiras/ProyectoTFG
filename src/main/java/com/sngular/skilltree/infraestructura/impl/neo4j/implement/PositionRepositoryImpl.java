package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import static com.sngular.skilltree.model.EnumLevelReq.MANDATORY;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.CandidateRepository;
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

  private final PositionCrudRepository crud;

  private final CandidateRepository candidateRepository;

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

    var positionNode = crud.save(mapper.toNode(position));

    return mapper.fromNode(positionNode);
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
