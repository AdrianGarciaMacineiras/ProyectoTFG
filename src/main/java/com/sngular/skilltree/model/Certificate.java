package com.sngular.skilltree.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;

@Builder(toBuilder = true)
public record Certificate(Long id, String code, String comments, LocalDate date) {
}
