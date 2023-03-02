package com.sngular.skilltree.application.updater;

import com.sngular.skilltree.contract.mapper.PeopleMapper;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.model.People;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeopleUpdaterImpl implements  PeopleUpdater{

    private final PeopleRepository peopleRepository;

    private final PeopleMapper mapper;

    @Override
    public People update(String personcode, People newPeople) {
        var oldperson = peopleRepository.findByCode(personcode);
        //mapper.update(oldperson, newPeople);
        return peopleRepository.save(newPeople);
    }

    @Override
    public People patch(String personcode, People patchedPeople) {
        var oldperson = peopleRepository.findByCode(personcode);
        var person = mapper.update(patchedPeople, oldperson);
        return peopleRepository.save(person);
    }
}
