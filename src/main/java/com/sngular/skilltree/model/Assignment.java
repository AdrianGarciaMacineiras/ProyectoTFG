package com.sngular.skilltree.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder(toBuilder = true)
public record Assignment(String id, String role, LocalDate initDate, LocalDate endDate, LocalDate assignDate, String dedication) {
}