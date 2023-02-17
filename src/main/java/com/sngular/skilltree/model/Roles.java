package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.Date;

@Builder(toBuilder = true)
public record Roles(String role, Date fromDate, Date enDate) {
}