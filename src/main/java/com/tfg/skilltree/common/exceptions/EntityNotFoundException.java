package com.tfg.skilltree.common.exceptions;

public class EntityNotFoundException extends RuntimeException {

    private static final String ERROR_MESSAGE = "Entity of type %s with code %s not found";

    public EntityNotFoundException(final String entityType, final String code) {
        super(String.format(ERROR_MESSAGE, entityType, code));
    }

    public EntityNotFoundException(final String entityType, final String positionCode, final String peopleCode) {
        super(String.format("No entity of type %s has been found related to the entities of type Position with code %s and entity of type People with code %s"
                , entityType, positionCode, peopleCode));
    }
}
