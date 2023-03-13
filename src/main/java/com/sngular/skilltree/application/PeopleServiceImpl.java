package com.sngular.skilltree.application;

import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    @Override
    public List<People> getAll() {
        return peopleRepository.findByDeletedIsFalse();
    }

    @Override
    public People create(People people) {
        validateExist(people.code());
        return peopleRepository.save(people);
    }

    @Override
    public People findByCode(Long personcode) {
        var people = peopleRepository.findByCode(personcode);
        if (Objects.isNull(people) || people.deleted())
            throw new EntityNotFoundException("People", personcode);
        return people;
    }

    @Override
    public People findPeopleByCode(Long personcode) {
        var people = peopleRepository.findPeopleByCode(personcode);
        if (Objects.isNull(people) || people.deleted())
            throw new EntityNotFoundException("People", personcode);
        return people;    }

    @Override
    public boolean deleteByCode(Long personCode) {
        validateDoesNotExist(personCode);
        return peopleRepository.deleteByCode(personCode);
    }

    private void validateExist(Long code) {
        var oldPerson = peopleRepository.findByCode(code);
        if (!Objects.isNull(oldPerson) && !oldPerson.deleted()) {
            throw new EntityFoundException("People", code);
        }
    }

    private void validateDoesNotExist(Long code) {
        var oldPerson = peopleRepository.findByCode(code);
        if (Objects.isNull(oldPerson) || oldPerson.deleted()) {
            throw new EntityNotFoundException("People", code);
        }
    }
}
