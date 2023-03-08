package com.sngular.skilltree.application;

import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ParticipateRelationship;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    @Override
    public List<People> getAll() {
        return peopleRepository.findAll();
    }

    @Override
    public People create(People people) {
        validateExist(people.code());
        return peopleRepository.save(people);
    }

    @Override
    public People findByCode(Integer personcode) {
        return peopleRepository.findByCode(personcode);
    }

    @Override
    public boolean deleteByCode(Integer personCode) {
        validateDoesntExist(personCode);
        return peopleRepository.deleteByCode(personCode);
    }

    private void validateExist(Integer code) {
        var oldPerson = peopleRepository.findByCode(code);
        if (!Objects.isNull(oldPerson)) {
            throw new EntityFoundException("People", code);
        }
    }

    private void validateDoesntExist(Integer code) {
        var oldPerson = peopleRepository.findByCode(code);
        if (Objects.isNull(oldPerson)) {
            throw new EntityNotFoundException("People", code);
        }
    }
}
