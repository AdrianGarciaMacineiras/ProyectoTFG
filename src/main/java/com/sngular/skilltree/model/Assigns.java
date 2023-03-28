package com.sngular.skilltree.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record Assigns(String role, LocalDate initDate, LocalDate endDate) {
}