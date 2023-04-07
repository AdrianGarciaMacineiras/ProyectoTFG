package com.sngular.skilltree.application;

import com.sngular.skilltree.model.Position;

import java.util.List;

public interface PositionService {

  List<Position> getAll();

  Position create(final Position toPosition);

  Position findByCode(final String opportunitycode);

  boolean deleteByCode(final String opportunitycode);
}
