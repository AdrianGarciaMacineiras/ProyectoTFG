package com.sngular.skilltree.infraestructura;

import java.util.List;

import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.model.PositionAssignment;

public interface PositionRepository {

  List<Position> findAll();

  Position save(Position position);

  Position findByCode(String positionCode);

    boolean deleteByCode(String positionCode);

  List<Position> findByDeletedIsFalse();

    List<Position> getPeopleAssignedPositions(String peopleCode);

  List<PositionAssignment> getPeopleAssignedToPosition(String positionCode);
}
