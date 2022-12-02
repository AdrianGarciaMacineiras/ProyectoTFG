package com.sngular.skilltree.project.repository.impl.neo4j.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class Tecnologia{
        String code;
        String nombre;

}