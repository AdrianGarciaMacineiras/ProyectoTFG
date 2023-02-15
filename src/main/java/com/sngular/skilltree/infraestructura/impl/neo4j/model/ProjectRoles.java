package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("ProjectRole")
public record ProjectRoles(@Id Long id, String rol, int number) {
}
