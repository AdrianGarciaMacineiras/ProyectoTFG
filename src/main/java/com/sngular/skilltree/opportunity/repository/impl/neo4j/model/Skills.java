package com.sngular.skilltree.opportunity.repository.impl.neo4j.model;

public record Skills(String skill, LevelReq levelReq, MinLevel minLevel, String minExp) {
}