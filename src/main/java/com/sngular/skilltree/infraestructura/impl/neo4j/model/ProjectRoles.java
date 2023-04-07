package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Builder;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("ProjectRole")
@Builder(toBuilder = true)
public record ProjectRoles(@Id @GeneratedValue Long id, String rol, String number) {
}
