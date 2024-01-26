package com.tfg.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.tfg.skilltree.common.config.CommonMapperConfiguration;
import com.tfg.skilltree.infraestructura.impl.neo4j.model.OfficeNode;
import com.tfg.skilltree.model.Office;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfiguration.class)
public interface OfficeNodeMapper {

    OfficeNode toNode(Office office);

    Office fromNode(OfficeNode officeNode);

    List<Office> map(List<OfficeNode> all);
}
