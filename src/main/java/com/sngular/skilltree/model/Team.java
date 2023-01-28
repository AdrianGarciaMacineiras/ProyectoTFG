package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Team(String code, String name, String description, List<String> tags, List<Member> members) {
}
