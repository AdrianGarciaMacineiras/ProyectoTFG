package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import com.sngular.skilltree.model.Office;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.OfficeNode;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OfficeNodeMapper {

    OfficeNode toNode(Office office);

    Office fromNode(OfficeNode officeNode);

    List<Office> map(List<OfficeNode> all);
}
