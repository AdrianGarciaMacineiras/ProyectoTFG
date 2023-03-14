package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Member(People people, EnumPosition position) {
}
