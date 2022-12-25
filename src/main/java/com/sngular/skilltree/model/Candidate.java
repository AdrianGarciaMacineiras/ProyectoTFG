package com.sngular.skilltree.model;

import java.util.List;

public record Candidate(String code, Opportunity opportunity, List<SkillsCandidate> skills) {
}
