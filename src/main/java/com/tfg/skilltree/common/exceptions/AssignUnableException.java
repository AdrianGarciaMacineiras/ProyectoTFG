package com.tfg.skilltree.common.exceptions;

public class AssignUnableException extends RuntimeException{
    private static final String ERROR_MESSAGE = "An entity of type %s with code %s cant be assigned";

    public AssignUnableException(final String entityType, final String code) {
        super(String.format(ERROR_MESSAGE, entityType, code));
    }

    public AssignUnableException(final String entityType) {
        super(String.format("It doesnt exists an entity of type %s to be assigned",entityType));
    }
}
