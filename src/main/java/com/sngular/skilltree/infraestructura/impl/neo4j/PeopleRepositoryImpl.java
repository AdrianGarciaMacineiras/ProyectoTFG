package com.sngular.skilltree.infraestructura.impl.neo4j;

import com.sngular.skilltree.model.People;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.mapper.PeopleNodeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PeopleRepositoryImpl implements PeopleRepository {

    private final PeopleCrudRepository crud;

    private final PeopleNodeMapper mapper;

    @Override
    public List<People> findAll() {
        return mapper.map(crud.findAll());
    }

    @Override
    public People save(People people) {
        return mapper.fromNode(crud.save(mapper.toNode(people)));
    }

    @Override
    public People findByCode(String personcode) {
        return mapper.fromNode(crud.findByCode(personcode));
    }

    @Override
    public boolean deleteByCode(String personcode) {
        var node = crud.findByCode(personcode);
        crud.delete(node);
        return true;
    }
}
