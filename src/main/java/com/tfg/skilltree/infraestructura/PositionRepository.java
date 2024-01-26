package com.tfg.skilltree.infraestructura;

import java.util.List;

import com.tfg.skilltree.model.Position;
import com.tfg.skilltree.model.PositionAssignment;
import com.tfg.skilltree.model.views.PositionView;

public interface PositionRepository {

  List<Position> findAll();

  List<PositionView> findAllResumed();

  Position save(Position position);

  boolean existByCode(String positionCode);

  Position findByCode(String positionCode);

    boolean deleteByCode(String positionCode);

  List<Position> findByDeletedIsFalse();

    List<Position> getPeopleAssignedPositions(String peopleCode);

  List<PositionAssignment> getPeopleAssignedToPosition(String positionCode);
}
