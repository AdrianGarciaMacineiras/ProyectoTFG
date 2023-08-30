package com.sngular.skilltree.infraestructura.impl.neo4j.model.converter;

import java.util.Objects;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.EnumLevelReq;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

public class EnumLevelReqConverter implements Neo4jPersistentPropertyConverter<EnumLevelReq> {
    @Override
    public Value write(EnumLevelReq source) {
        if (Objects.isNull(source)) {
            throw new NullPointerException("Enum is null");
        }
        return new StringValue(source.getValue());
    }

    @Override
    public EnumLevelReq read(Value source) {
        return EnumLevelReq.from(source.asString());
    }
}
