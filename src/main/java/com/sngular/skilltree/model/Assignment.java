package com.sngular.skilltree.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record Assignment(Long id, String role, LocalDate initDate, LocalDate endDate, LocalDate assignDate) {
}