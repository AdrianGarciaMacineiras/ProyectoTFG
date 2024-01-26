package com.tfg.skilltree.model;

import lombok.Builder;

@Builder(toBuilder = true)
public record Office(String code, String name, String address, String phone, String geolocation) {
}
