package com.sngular.skilltree.infraestructura.impl.neo4j.model.converter;

import java.util.Objects;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.EnumGuards;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

public class EnumGuardConverter implements Neo4jPersistentPropertyConverter<EnumGuards> {
    @Override
    public Value write(EnumGuards source) {
        if (Objects.isNull(source)) {
            throw new NullPointerException("Enum is null");
        }
        return new StringValue(source.getValue());
    }

    @Override
    public EnumGuards read(Value source) {
        return EnumGuards.from(source.asString());
    }
}