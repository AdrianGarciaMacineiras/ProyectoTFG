package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;

@Builder(toBuilder = true)
public record Candidate(String code, People candidate, String status, Opportunity opportunity, boolean deleted) {
}
