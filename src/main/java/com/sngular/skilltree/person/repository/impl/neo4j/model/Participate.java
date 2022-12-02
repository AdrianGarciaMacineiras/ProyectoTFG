package com.sngular.skilltree.person.repository.impl.neo4j.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@RequiredArgsConstructor
public class Participate{
        String code;
        List<Roles> roles;
}