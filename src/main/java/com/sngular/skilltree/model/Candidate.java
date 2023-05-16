package com.sngular.skilltree.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder(toBuilder = true)
public record Candidate(String id, String code, People candidate, EnumStatus status, LocalDate introductionDate,
                        LocalDate resolutionDate, LocalDate creationDate, LocalDate interviewDate, Position position, List<Knows> skills) {
}
