package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import java.util.List;

public interface CustomTeamRepository {

    <T> List<T> findByDeletedIsFalse(Class<T> type);

    <T> T findByCodeAndDeletedIsFalse(String code, Class<T> type);

    <T> T findByShortNameAndDeletedIsFalse(String code, Class<T> type);

}
