package com.sngular.skilltree.model.views;

import lombok.Builder;

@Builder(toBuilder = true)
public record PeopleNamesView(String code, String name, String surname) {
}
