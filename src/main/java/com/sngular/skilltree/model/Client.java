package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Client(String code, String name, String industry, String country, String principalOffice, String HQ,
                     List<Office> offices) {

}