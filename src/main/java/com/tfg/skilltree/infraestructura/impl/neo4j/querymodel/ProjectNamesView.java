package com.tfg.skilltree.infraestructura.impl.neo4j.querymodel;

import lombok.Builder;

@Builder
public record ProjectNamesView(String code, String name) {
}
