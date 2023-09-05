package com.sngular.skilltree.infraestructura.impl.neo4j.querymodel;

public interface PositionView{
    String getCode();

    String getName();

    String getMode();

    String getRole();

    ProjectView project();
}
