package com.sngular.skilltree.opportunity.repository.impl.neo4j.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Skill{
        String skill;
        LevelReq levelReq;
        MinLevel minLevel;
        String minExp;
    }