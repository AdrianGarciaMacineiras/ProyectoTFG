package com.sngular.skilltree.model;

import lombok.Builder;

@Builder
public record Office(String code, String name, String address, String phone, String geolocation) {
}
