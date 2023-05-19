package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import lombok.Builder;
import org.springframework.data.neo4j.core.schema.*;

import java.time.LocalDate;

@Node("Role")
@Builder(toBuilder = true)
public record Role(@Id String id, String role, String category, LocalDate initDate, boolean deleted) {
};