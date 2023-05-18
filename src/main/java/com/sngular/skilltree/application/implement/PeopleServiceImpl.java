package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.application.PeopleService;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.model.Candidate;
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

    private final CandidateService candidateService;

    @Override
    public List<People> getAll() {
        return peopleRepository.findAll();
    }

    @Override
    public People create(People people) {
        validateExist(people.code());
        People.PeopleBuilder builder = people.toBuilder();
        People aux = builder.assignable(true).build();
        return peopleRepository.save(aux);
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

    @Override
    public People assignCandidate(Long peopleCode, String positionCode) {
        validateDoesNotExist(peopleCode);
        candidateService.assignCandidate(positionCode, peopleCode);
        return peopleRepository.findByCode(peopleCode);
    }

    @Override
    public List<Candidate> getCandidates(Long peopleCode) {
        validateDoesNotExist(peopleCode);
        return candidateService.getCandidates(peopleCode);
    }

    @Override
    public List<People> getPeopleSkills(List<String> skills) {
        return peopleRepository.getPeopleSkills(skills);
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
