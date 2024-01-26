package com.tfg.skilltree.model;

import java.time.LocalDate;

import lombok.Builder;

@Builder(toBuilder = true)
public record Certificate(String id, String code, String name, String comment, LocalDate date) {
}
