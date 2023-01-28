package com.sngular.skilltree.model;

import lombok.Builder;

@Builder
public record Member(People people, EnumPosition position) {
}
