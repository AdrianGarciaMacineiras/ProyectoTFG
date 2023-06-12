package com.sngular.skilltree.infraestructura.impl.neo4j.customrepository;

import java.util.List;

public interface CustomTeamRepository {

    <T> List<T> findByDeletedIsFalse(Class<T> type);
}
