package com.sngular.skilltree.infraestructura.impl.neo4j.querymodel;

import com.sngular.skilltree.infraestructura.impl.neo4j.model.converter.LocalDateConverter;
import lombok.Builder;
import org.springframework.data.neo4j.core.convert.ConvertWith;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record Assigned(String id, PositionView position,
                       @ConvertWith(converter = LocalDateConverter.class) LocalDate endDate,
                       @ConvertWith(converter = LocalDateConverter.class) LocalDate initDate,
                       @ConvertWith(converter = LocalDateConverter.class) LocalDate assignDate,
                       String role, Integer dedication) {
}