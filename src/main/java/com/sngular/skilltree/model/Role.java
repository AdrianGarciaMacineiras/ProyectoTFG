package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.Date;


@Builder(toBuilder = true)
public record Role(Long id, String role, String category, Date initDate) {
}
