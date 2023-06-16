package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record Client(String code, String name, String industry, String country, List<Office> offices, boolean deleted) {

}