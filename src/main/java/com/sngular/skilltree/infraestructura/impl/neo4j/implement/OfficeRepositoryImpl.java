package com.sngular.skilltree.infraestructura.impl.neo4j.implement;

import java.util.List;

import com.sngular.skilltree.infraestructura.OfficeRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.OfficeCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.OfficeNodeMapper;
import com.sngular.skilltree.model.Office;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OfficeRepositoryImpl implements OfficeRepository {

    private final OfficeCrudRepository crud;

    private final OfficeNodeMapper mapper;

    @Override
    public Office findByCode(String officecode) {
        return mapper.fromNode(crud.findByCode(officecode));
    }

    @Override
    public List<Office> findAll() {
        var office = crud.findAll();
        return mapper.map(office);
    }
}
