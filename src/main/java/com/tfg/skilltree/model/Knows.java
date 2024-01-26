package com.tfg.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Knows(String id, String code, String name, Integer experience, String level, Boolean primary) {
}