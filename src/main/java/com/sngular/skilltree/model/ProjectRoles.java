package com.sngular.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record ProjectRoles(Long id, String rol, int number) {
}
