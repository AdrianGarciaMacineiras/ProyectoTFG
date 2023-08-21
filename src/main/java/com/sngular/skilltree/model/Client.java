package com.sngular.skilltree.model;

import java.util.List;

import lombok.Builder;

@Builder(toBuilder = true)
public record Client(String code, String name, String industry, String country, List<Office> offices, boolean deleted) {

}