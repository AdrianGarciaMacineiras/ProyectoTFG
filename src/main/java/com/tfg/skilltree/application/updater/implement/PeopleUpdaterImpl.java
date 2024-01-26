package com.tfg.skilltree.application.updater.implement;

import java.util.Objects;

import com.tfg.skilltree.application.updater.PeopleUpdater;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.contract.mapper.PeopleMapper;
import com.tfg.skilltree.infraestructura.PeopleRepository;
import com.tfg.skilltree.infraestructura.impl.neo4j.PeopleCrudRepository;
import com.tfg.skilltree.model.People;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeopleUpdaterImpl implements PeopleUpdater {

    private final PeopleRepository peopleRepository;

    private final PeopleMapper mapper;

    private final PeopleCrudRepository crud;

    @Override
    public People update(final String personCode, final People newPeople) {
        validate(personCode);
        var oldPerson = peopleRepository.findByCode(personCode);
        if (Objects.isNull(oldPerson)) {
            throw new EntityNotFoundException("People", personCode);
        }
        return peopleRepository.save(newPeople);
    }

    @Override
    public People patch(final String personCode, final People patchedPeople) {
        validate(personCode);
        var oldPerson = peopleRepository.findByCode(personCode);
        var person = mapper.patch(patchedPeople, oldPerson);
        return peopleRepository.save(person);
    }

    private void validate(final String code) {
        var oldPerson = peopleRepository.findByCode(code);
        if (Objects.isNull(oldPerson) || oldPerson.deleted()) {
            throw new EntityNotFoundException("People", code);
        }
    }
}
