package com.sngular.skilltree.infraestructura.impl.neo4j.model;

import org.springframework.data.neo4j.core.schema.RelationshipProperties;

import java.util.Date;

@RelationshipProperties
public record CertificateRelationship(String code, String comment, Date date) {
}
