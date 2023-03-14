package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import java.time.LocalDate;
import java.util.Date;

public record Roles(String role, LocalDate fromDate, String enDate) {
};