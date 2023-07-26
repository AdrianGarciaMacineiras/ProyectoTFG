package com.sngular.skilltree.model;

import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder(toBuilder = true)
public record Team(String code,
                   String name,
                   String shortName,
                   String description,
                   List<String> tags,
                   List<Member> members,
                   List<Skill> strategics,
                   LocalDateTime lastUpdateTime,
                   boolean deleted) {
}
