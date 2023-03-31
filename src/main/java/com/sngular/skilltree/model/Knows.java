package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Knows(Long id, String code, Integer experience, String level, Boolean primary) {
}