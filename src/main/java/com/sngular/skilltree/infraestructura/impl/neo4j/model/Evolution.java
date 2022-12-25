package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import java.util.Date;


public record Evolution(String title, String category, Date fromDate) {
};