package com.sngular.skilltree.person.repository.impl.neo4j.model;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.Date;

@Value
@RequiredArgsConstructor
public class Roles{
            String role;
            Date fromDate;
            Date enDate;
};