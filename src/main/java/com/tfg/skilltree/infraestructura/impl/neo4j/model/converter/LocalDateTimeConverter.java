package com.tfg.skilltree.infraestructura.impl.neo4j.model.converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.driver.Value;
import org.neo4j.driver.internal.value.DateTimeValue;
import org.neo4j.driver.internal.value.DateValue;
import org.springframework.data.neo4j.core.convert.Neo4jPersistentPropertyConverter;

@Slf4j
public class LocalDateTimeConverter implements Neo4jPersistentPropertyConverter<LocalDateTime> {

  private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm'Z'");

  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  @Override
  public Value write(LocalDateTime source) {
    var offset = OffsetDateTime.now().toZonedDateTime();
    return new DateTimeValue(ZonedDateTime.ofInstant(source, offset.getOffset(), offset.getZone()));
  }

  @Override
  public LocalDateTime read(Value source) {
    if (source instanceof DateValue dateValue) {
      LocalDate localDate = dateValue.asLocalDate();
      return localDate.atStartOfDay();
    } else if (source instanceof DateTimeValue dateTimeValue) {
      return dateTimeValue.asZonedDateTime().toLocalDateTime();
    } else {
      final var valueStr = source.toString();
      if (valueStr.matches("yyyy-MM-dd")) {
        return LocalDate.parse(valueStr, DATE_FORMATTER).atStartOfDay();
      }
      return LocalDateTime.parse(valueStr, DATE_TIME_FORMATTER);
    }
  }
}