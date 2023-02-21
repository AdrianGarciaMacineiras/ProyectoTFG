package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.*;

import java.util.Date;

@Node("Role")
public record Role(@Id @GeneratedValue Long id, String role, String category, String fromDate) {
};