package com.tfg.skilltree.infraestructura.impl.neo4j.model.converter;

import java.util.Objects;

import com.tfg.skilltree.infraestructura.impl.neo4j.model.EnumCharge;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;
import org.springframework.lang.Nullable;

public class EnumChargeConverter implements Neo4jPersistentPropertyConverter<EnumCharge> {
    @Override
    public Value write(@Nullable EnumCharge source) {
        if (Objects.isNull(source)) {
            throw new NullPointerException("Enum is null");
        }
        return new StringValue(source.getValue());
    }

    @Override
    public EnumCharge read(Value source) {
        return EnumCharge.from(source.asString());
    }
}
