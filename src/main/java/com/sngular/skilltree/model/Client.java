package com.sngular.skilltree.model;

import java.util.List;

public record Client(String code, String name, String industry, String country, String principalOffice, String HQ,
                     List<Office> offices) {

}