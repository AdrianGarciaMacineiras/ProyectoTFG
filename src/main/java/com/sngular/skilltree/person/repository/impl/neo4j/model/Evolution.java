package com.sngular.skilltree.person.repository.impl.neo4j.model;

import java.util.Date;


public record Evolution(String title, String category, Date fromDate) {
};