package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import java.util.List;


public interface CustomPeopleRepository {

    <T> List<T> findByDeletedIsFalse(Class<T> type);

    <T> T findByCode(String code, Class<T> type);
}
