package com.sngular.skilltree.model.views;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProjectNamesView(String code, String name) {
}