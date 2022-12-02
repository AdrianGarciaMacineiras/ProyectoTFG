package com.sngular.skilltree.person.repository.impl.neo4j.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Knows{
        String code;
        String level;
};
