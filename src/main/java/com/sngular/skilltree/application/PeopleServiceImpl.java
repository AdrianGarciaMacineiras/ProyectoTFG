package com.sngular.skilltree.application;

import com.sngular.skilltree.contract.mapper.PeopleMapper;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    private final PeopleMapper mapper;

    @Override
    public List<People> getAll() {
        return peopleRepository.findAll();
    }

    @Override
    public People create(People people) {
        validate(people);
        return peopleRepository.save(people);
    }

    @Override
    public People findByCode(String personcode) {
        return peopleRepository.findByCode(personcode);
    }

    @Override
    public boolean deleteByCode(String personcode) {
        return peopleRepository.deleteByCode(personcode);
    }

    @Override
    public People update(String personcode, People newPeople) {
        var oldperson = peopleRepository.findByCode(personcode);
        mapper.update(oldperson, newPeople);
        return peopleRepository.save(oldperson);
    }

    @Override
    public People patch(String personcode, People patchedPeople) {
        var oldperson = peopleRepository.findByCode(personcode);
        mapper.update(oldperson, patchedPeople);
        return peopleRepository.save(oldperson);    }

    private void validate(People people) {
    }
}
