package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.Date;

@Builder(toBuilder = true)
public record Certificate(String code, String comment, Date date) {
}
