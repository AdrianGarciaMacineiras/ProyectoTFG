package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Member(String id, People people, String charge) {
}
