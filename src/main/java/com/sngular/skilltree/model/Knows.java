package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Knows(String code, String level, Boolean primary) {
}