package com.sngular.skilltree.common.exceptions;

public class EntityFoundException extends RuntimeException{
    private static final String ERROR_MESSAGE = "An entity of type %s with code %s found";

    public EntityFoundException(final String entityType, final Integer code) {
        super(String.format(ERROR_MESSAGE, entityType, code));
    }

    public EntityFoundException(final String entityType, final String code) {
        super(String.format(ERROR_MESSAGE, entityType, code));
    }
}
