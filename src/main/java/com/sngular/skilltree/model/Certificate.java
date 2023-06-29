package com.sngular.skilltree.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.Date;

@Builder(toBuilder = true)
public record Certificate(String id, String code, String name,String comments, LocalDate date) {
}
