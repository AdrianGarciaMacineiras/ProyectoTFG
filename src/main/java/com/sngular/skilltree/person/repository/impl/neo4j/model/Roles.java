package com.sngular.skilltree.person.repository.impl.neo4j.model;

import java.util.Date;



public record Roles(String role, Date fromDate, Date enDate) {
};