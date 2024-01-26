package com.tfg.skilltree.infraestructura.impl.neo4j.querymodel;

import java.util.List;

import lombok.Builder;

@Builder
public record PeopleExtendedView(String code, String employeeId, String name, String surname, String title,
                                 String teamShortName, List<KnowsView> knows, boolean assignable) {

    @Builder
    public record KnowsView (String code, String name, Integer experience, String level, boolean primary) {}
}
