package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.PositionNode;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PeopleExtendedView;
import com.sngular.skilltree.infraestructura.impl.neo4j.querymodel.PositionExtendedView;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface CustomPositionRepository {

    List<PositionExtendedView> getAllPositionExtended();

}
