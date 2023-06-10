package com.sngular.skilltree.infraestructura.impl.neo4j.model.converter;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.EnumMode;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

import java.util.Objects;

public class EnumModeConverter implements Neo4jPersistentPropertyConverter<EnumMode> {
    @Override
    public Value write(EnumMode source) {
        if (Objects.isNull(source)) {
            throw new NullPointerException("Enum is null");
        }
        return new StringValue(source.getValue());
    }

    @Override
    public EnumMode read(Value source) {
        return EnumMode.from(source.asString());
    }
}