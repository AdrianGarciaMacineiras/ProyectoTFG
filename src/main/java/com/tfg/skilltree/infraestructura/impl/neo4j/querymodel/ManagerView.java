package com.tfg.skilltree.infraestructura.impl.neo4j.querymodel;

import lombok.Builder;

@Builder
public record ManagerView(String code, String employeeId, String name, String surname, String title,
                          boolean assignable) {
}
