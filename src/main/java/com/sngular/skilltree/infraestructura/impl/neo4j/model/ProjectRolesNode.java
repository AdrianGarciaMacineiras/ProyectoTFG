package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Builder;
import org.springframework.data.neo4j.core.schema.ElementId;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("ProjectRole")
@Builder(toBuilder = true)
public record ProjectRolesNode(@Id ElementId id, String rol, String number) {
}
