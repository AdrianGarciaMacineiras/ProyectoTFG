package com.sngular.skilltree.application.implement;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.application.PeopleService;
import com.sngular.skilltree.application.PositionService;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.model.Position;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    private final CandidateService candidateService;

    private final PositionService positionService;

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
    public People findByCode(Long personCode) {
        var people = peopleRepository.findByCode(personCode);
        if (Objects.isNull(people) || people.deleted())
            throw new EntityNotFoundException("People", personCode);
        var candidacies = candidateService.getCandidates(personCode);
        if(!Objects.isNull(people.candidacies()))
            people.candidacies().clear();
        var newperson = people.toBuilder().candidacies(candidacies).build();
        return newperson;
    }

    @Override
    public People findPeopleByCode(Long personcode) {
        var people = peopleRepository.findPeopleByCode(personcode);
        if (Objects.isNull(people) || people.deleted())
            throw new EntityNotFoundException("People", personcode);
        return people;
    }

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

    @Override
    public List<People> getOtherPeopleStrategicSkills(String teamcode) {
        return peopleRepository.getOtherPeopleStrategicSkills(teamcode);
    }

    @Override
    public List<Position> getPeopleAssignedPositions(Long peoplecode) {
        validateDoesNotExist(peoplecode);
        return positionService.getPeopleAssignedPositions(peoplecode);
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
