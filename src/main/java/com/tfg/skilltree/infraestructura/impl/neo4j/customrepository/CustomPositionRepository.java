package com.tfg.skilltree.infraestructura.impl.neo4j.customrepository;

import com.tfg.skilltree.infraestructura.impl.neo4j.querymodel.PositionExtendedView;

import java.util.List;

public interface CustomPositionRepository {

    List<PositionExtendedView> getAllPositionExtended();

}
