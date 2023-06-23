package com.sngular.skilltree.infraestructura.impl.neo4j.querymodel;

import lombok.Builder;

@Builder
public record ClientView(String code, String name) {
}
