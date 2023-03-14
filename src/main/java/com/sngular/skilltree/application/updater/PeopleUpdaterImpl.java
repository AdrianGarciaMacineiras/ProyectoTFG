package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.contract.mapper.PeopleMapper;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.sngular.skilltree.infraestructura.impl.neo4j.model.ParticipateRelationship;
import com.sngular.skilltree.model.Participate;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Roles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PeopleUpdaterImpl implements  PeopleUpdater{

    private final PeopleRepository peopleRepository;

    private final PeopleMapper mapper;

    private final PeopleCrudRepository crud;

    @Override
    public People update(Long personcode, People newPeople) {
        validate(personcode);
        crud.detachDelete(personcode);
        return peopleRepository.save(newPeople);
    }

    @Override
    public People patch(Long personcode, People patchedPeople) {
        validate(personcode);
        var oldperson = peopleRepository.findByCode(personcode);
        var person = mapper.update(patchedPeople, oldperson);
        return peopleRepository.save(person);
    }

    private void validate(Long code) {
        var oldPerson = peopleRepository.findByCode(code);
        if (Objects.isNull(oldPerson) || oldPerson.deleted()) {
            throw new EntityNotFoundException("People", code);
        }
    }
}
