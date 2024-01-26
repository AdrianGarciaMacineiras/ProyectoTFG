package com.tfg.skilltree.model;

import lombok.Builder;

@Builder
public record PeopleSkill(String skillCode, EnumLevel level, Integer minExp) {
}
