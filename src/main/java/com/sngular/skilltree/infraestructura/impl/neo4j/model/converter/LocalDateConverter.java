package com.sngular.skilltree.infraestructura.impl.neo4j.model.converter;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.DateValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class LocalDateConverter implements Neo4jPersistentPropertyConverter<LocalDate> {

    private static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");

    @Override
    public Value write(LocalDate source) {
        return new DateValue(source);
    }

    @Override
    public LocalDate read(Value source) {
        return LocalDate.parse(source.toString(), PATTERN);
    }
}
