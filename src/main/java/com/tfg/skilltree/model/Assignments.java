package com.tfg.skilltree.model;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record Assignments(String name, List<Assignment> assignments) {
}