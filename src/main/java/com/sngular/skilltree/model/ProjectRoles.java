package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProjectRoles(String id, String rol, int number) {
}
