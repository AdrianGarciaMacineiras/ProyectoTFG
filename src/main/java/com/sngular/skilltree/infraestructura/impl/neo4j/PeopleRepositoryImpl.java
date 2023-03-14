package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ParticipateRelationship;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.management.InstanceNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PeopleRepositoryImpl implements PeopleRepository {

    private final PeopleCrudRepository crud;

    private final PeopleNodeMapper mapper;

    @Override
    public List<People> findAll() {
        return mapper.map(crud.findByDeletedIsFalse());
    }

    @Override
    public People save(People people) {
        return mapper.fromNode(crud.save(mapper.toNode(people)));
    }

    @Override
    public People findByCode(Long personcode) {
        return mapper.fromNode(crud.findByCode(personcode));
    }

    @Override
    public People findPeopleByCode(Long personcode) {
        return mapper.fromNode(crud.findPeopleByCode(personcode));
    }

    @Override
    public boolean deleteByCode(Long personcode) {
        var node = crud.findByCode(personcode);
        node.setDeleted(true);
        crud.save(node);
        return true;
    }

    @Override
    public List<People> findByDeletedIsFalse() {
        return mapper.map(crud.findByDeletedIsFalse());
    }
}
