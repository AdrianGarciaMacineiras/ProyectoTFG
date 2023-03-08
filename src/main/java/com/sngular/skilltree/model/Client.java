package com.sngular.skilltree.model;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record Client(Integer code, String name, String industry, String country, String principalOffice, String HQ,
                     List<Office> offices, boolean deleted) {

}