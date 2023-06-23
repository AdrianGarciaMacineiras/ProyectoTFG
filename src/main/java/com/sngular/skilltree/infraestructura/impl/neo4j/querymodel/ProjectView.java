package com.sngular.skilltree.infraestructura.impl.neo4j.querymodel;

import lombok.Builder;

@Builder
public record ProjectView(String code, String name, ClientView client) {
}
