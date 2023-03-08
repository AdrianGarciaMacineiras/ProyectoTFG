package com.sngular.skilltree.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Date;

@Builder(toBuilder = true)
public record Roles(String role, LocalDate initDate, LocalDate endDate) {
}