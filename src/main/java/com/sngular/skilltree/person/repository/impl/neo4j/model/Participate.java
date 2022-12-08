package com.sngular.skilltree.person.repository.impl.neo4j.model;

import java.util.List;


public record Participate(String code, List<Roles> roles) {
}