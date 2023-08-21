package com.sngular.skilltree.model;

import java.time.LocalDate;

import lombok.Builder;

@Builder(toBuilder = true)
public record Assignment(String id, String role, LocalDate initDate, LocalDate endDate, LocalDate assignDate, Integer dedication) {
}