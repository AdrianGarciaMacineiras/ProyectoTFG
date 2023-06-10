package com.sngular.skilltree.infraestructura.impl.neo4j.model.converter;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.EnumLevel;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

import java.util.Objects;

public class EnumLevelConverter implements Neo4jPersistentPropertyConverter<EnumLevel> {
    @Override
    public Value write(EnumLevel source) {
        if (Objects.isNull(source)) {
            throw new NullPointerException("Enum is null");
        }
        return new StringValue(source.getValue());
    }

    @Override
    public EnumLevel read(Value source) {
        return EnumLevel.from(source.asString());
    }
}
