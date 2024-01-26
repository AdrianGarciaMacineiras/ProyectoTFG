package com.tfg.skilltree.infraestructura.impl.neo4j.model.converter;

import java.util.Objects;

import com.tfg.skilltree.infraestructura.impl.neo4j.model.EnumMinLevel;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

public class EnumMinLevelConverter implements Neo4jPersistentPropertyConverter<EnumMinLevel> {
    @Override
    public Value write(EnumMinLevel source) {
        if (Objects.isNull(source)) {
            throw new NullPointerException("Enum is null");
        }
        return new StringValue(source.getValue());
    }

    @Override
    public EnumMinLevel read(Value source) {
        return EnumMinLevel.from(source.asString());
    }
}
