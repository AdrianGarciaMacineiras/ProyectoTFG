package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Member(Long id, People people, EnumCharge charge) {
}
