package com.sngular.skilltree.infraestructura.impl.neo4j.common.exceptions;

public class PositionWithoutProjectException extends RuntimeException{
    private static final String ERROR_MESSAGE = "An entity of type %s with code %s doesn't have a project";

    public PositionWithoutProjectException(final String entityType, final String code) {
        super(String.format(ERROR_MESSAGE, entityType, code));
    }

    public PositionWithoutProjectException(final String entityType) {
        super(String.format("It doesn't exist an entity of type %s to be assigned",entityType));
    }
}
