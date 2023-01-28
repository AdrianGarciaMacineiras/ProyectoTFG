package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.Date;

@Builder
public record Certificate(String code, String comment, Date date) {
}
