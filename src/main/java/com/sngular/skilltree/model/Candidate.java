package com.sngular.skilltree.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Candidate(String code, Opportunity opportunity, List<SkillsCandidate> skills) {
}
