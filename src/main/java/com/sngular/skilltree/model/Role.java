package com.sngular.skilltree.model;

import java.util.Date;

import lombok.Builder;


@Builder(toBuilder = true)
public record Role(String id, String role, String category, Date initDate) {
}
