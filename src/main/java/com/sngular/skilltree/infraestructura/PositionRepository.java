package com.sngular.skilltree.infraestructura;

import com.sngular.skilltree.model.Position;

import java.util.List;

public interface PositionRepository {

  List<Position> findAll();

  Position save(Position position);

  Position findByCode(String positioncode);

  boolean deleteByCode(String positioncode);

  List<Position> findByDeletedIsFalse();

  Position findPositionCandidates(String positioncode);
}
