package com.sngular.skilltree.common.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Entity of type %s with code %s not found";

    public EntityNotFoundException(final String entityType, final Integer code) {
        super(String.format(ERROR_MESSAGE, entityType, code));
    }

    public EntityNotFoundException(final String entityType, final String code) {
        super(String.format(ERROR_MESSAGE, entityType, code));
    }
}
