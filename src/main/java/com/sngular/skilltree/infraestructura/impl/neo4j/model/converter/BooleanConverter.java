package com.sngular.skilltree.infraestructura.impl.neo4j.model.converter;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.BooleanValue;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

@Slf4j
public class BooleanConverter implements Neo4jPersistentPropertyConverter<Boolean> {

    @Override
    public Value write(final Boolean source) {
        return BooleanValue.fromBoolean(source);
    }

    @Override
    public Boolean read(final Value source) {
        if (source instanceof StringValue) {
            return Boolean.parseBoolean(((StringValue) source).asString().toLowerCase());
        } else if (source instanceof BooleanValue) {
            return ((BooleanValue) source).asBoolean();
        } else {
            return false;
        }
    }
}
