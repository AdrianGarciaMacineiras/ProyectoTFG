package com.sngular.skilltree.common.exceptions;

public class AssignUnableException extends RuntimeException{
    private static final String ERROR_MESSAGE = "An entity of type %s with code %s cant be assigned";

    public AssignUnableException(final String entityType, final Long code) {
        super(String.format(ERROR_MESSAGE, entityType, code));
    }
}
