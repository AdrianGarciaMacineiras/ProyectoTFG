package com.tfg.skilltree.infraestructura.impl.neo4j.implement;

import java.util.List;

import com.tfg.skilltree.infraestructura.OfficeRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.OfficeCrudRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.mapper.OfficeNodeMapper;
import com.tfg.skilltree.model.Office;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OfficeRepositoryImpl implements OfficeRepository {

    private final OfficeCrudRepository crud;

    private final OfficeNodeMapper mapper;

    @Override
    public Office findByCode(String officeCode) {
        return mapper.fromNode(crud.findByCode(officeCode));
    }

    @Override
    public List<Office> findAll() {
        var office = crud.findAll();
        return mapper.map(office);
    }
}
