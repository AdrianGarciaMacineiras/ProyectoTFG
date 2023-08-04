package com.sngular.skilltree.infraestructura.impl.neo4j.querymodel;

import lombok.Builder;

@Builder
public record PositionView(String code, String name, String mode, String role, ProjectView project) {
}
