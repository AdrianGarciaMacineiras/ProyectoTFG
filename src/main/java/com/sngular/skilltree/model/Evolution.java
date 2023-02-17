package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.Date;


@Builder(toBuilder = true)
public record Evolution(String role, String category, Date fromDate) {
}
