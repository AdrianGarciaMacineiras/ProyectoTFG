package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import java.time.LocalDate;

import lombok.Builder;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Role")
@Builder(toBuilder = true)
public record Role(@Id String id, String role, String category, LocalDate initDate, boolean deleted) {
}