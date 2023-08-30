package com.sngular.skilltree.infraestructura.impl.neo4j.model.converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.DateTimeValue;
import org.neo4j.driver.internal.value.DateValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

@Slf4j
public class LocalDateConverter implements Neo4jPersistentPropertyConverter<LocalDate> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Value write(LocalDate source) {
        return new DateValue(source);
    }

    @Override
    public LocalDate read(Value source) {
        if (source instanceof DateValue) {
            return ((DateValue) source).asLocalDate();
        } else if (source instanceof DateTimeValue) {
            return ((DateTimeValue) source).asZonedDateTime().toLocalDate();
        } else {
            final var valueStr = source.toString();
            if (valueStr.matches("yyyy-MM-dd")) {
                return LocalDate.parse(valueStr, DATE_FORMATTER);
            }
            return LocalDate.parse(valueStr, DATE_TIME_FORMATTER);
        }
    }
}
