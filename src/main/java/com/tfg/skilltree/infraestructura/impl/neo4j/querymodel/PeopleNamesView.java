package com.tfg.skilltree.infraestructura.impl.neo4j.querymodel;

import lombok.Builder;

@Builder
public record PeopleNamesView(String code, String name, String surname) {
}
