package com.sngular.skilltree.infraestructura.impl.neo4j.mapper;

import java.util.List;

import com.sngular.skilltree.common.config.CommonMapperConfiguration;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.OfficeNode;
import com.sngular.skilltree.model.Office;
import org.mapstruct.Mapper;

@Mapper(config = CommonMapperConfiguration.class)
public interface OfficeNodeMapper {

    OfficeNode toNode(Office office);

    Office fromNode(OfficeNode officeNode);

    List<Office> map(List<OfficeNode> all);
}
