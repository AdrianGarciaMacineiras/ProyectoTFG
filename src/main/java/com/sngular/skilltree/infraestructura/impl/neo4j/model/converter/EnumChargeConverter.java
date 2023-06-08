package com.sngular.skilltree.infraestructura.impl.neo4j.model.converter;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.EnumCharge;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.StringValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

public class EnumChargeConverter implements Neo4jPersistentPropertyConverter<EnumCharge> {
    @Override
    public Value write(EnumCharge source) {
        assert source != null;
        return new StringValue(source.getValue());
    }

    @Override
    public EnumCharge read(Value source) {
        return EnumCharge.from(source.asString());
    }
}
