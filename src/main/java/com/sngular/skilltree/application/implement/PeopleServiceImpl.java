package com.sngular.skilltree.application.implement;

import java.util.List;
import java.util.Objects;

import com.sngular.skilltree.application.CandidateService;
import com.sngular.skilltree.application.PeopleService;
import com.sngular.skilltree.application.PositionService;
import com.sngular.skilltree.common.exceptions.EntityFoundException;
import com.sngular.skilltree.common.exceptions.EntityNotFoundException;
import com.sngular.skilltree.infraestructura.PeopleRepository;
import com.sngular.skilltree.model.Candidate;
import com.sngular.skilltree.model.People;
import com.sngular.skilltree.model.Position;
import com.sngular.skilltree.model.views.PeopleView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
    public List<PeopleView> getAllResumed() {
        return peopleRepository.findAllResumed();
    }

    @Override
    public People create(People people) {
        validateExist(people.code());
        People.PeopleBuilder builder = people.toBuilder();
        People aux = builder.assignable(true).build();
        return peopleRepository.save(aux);
    }

    @Override
    public People findByCode(String peopleCode) {
        var people = peopleRepository.findByCode(peopleCode);
        if (Objects.isNull(people) || people.deleted())
            throw new EntityNotFoundException("People", peopleCode);
        var candidacies = candidateService.getCandidatesByPosition(peopleCode);
        if (!Objects.isNull(people.candidacies()))
            people.candidacies().clear();
        return people.toBuilder().candidacies(candidacies).build();
    }

    @Override
    public People findPeopleByCode(String peopleCode) {
        var people = peopleRepository.findPeopleByCode(peopleCode);
        if (Objects.isNull(people) || people.deleted())
            throw new EntityNotFoundException("People", peopleCode);
        return people;
    }

    @Override
    public boolean deleteByCode(String peopleCode) {
        validateDoesNotExist(peopleCode);
        return peopleRepository.deleteByCode(peopleCode);
    }

    @Override
    public People assignCandidate(String peopleCode, String positionCode) {
        validateDoesNotExist(peopleCode);
        candidateService.assignCandidate(positionCode, peopleCode);
        return peopleRepository.findByCode(peopleCode);
    }

    @Override
    public List<Candidate> getCandidates(String peopleCode) {
        validateDoesNotExist(peopleCode);
        return candidateService.getCandidatesByPeople(peopleCode);
    }

    @Override
    public List<People> getPeopleSkills(List<String> skills) {
        return peopleRepository.getPeopleSkills(skills);
    }

    @Override
    public List<People> getOtherPeopleStrategicSkills(String teamCode) {
        return peopleRepository.getOtherPeopleStrategicSkills(teamCode);
    }

    @Override
    public List<Position> getPeopleAssignedPositions(String peopleCode) {
        validateDoesNotExist(peopleCode);
        return positionService.getPeopleAssignedPositions(peopleCode);
    }

    private void validateExist(String code) {
        var oldPerson = peopleRepository.findByCode(code);
        if (!Objects.isNull(oldPerson) && !oldPerson.deleted()) {
            throw new EntityFoundException("People", code);
        }
    }

    private void validateDoesNotExist(String code) {
        var oldPerson = peopleRepository.findByCode(code);
        if (Objects.isNull(oldPerson) || oldPerson.deleted()) {
            throw new EntityNotFoundException("People", code);
        }
    }
}
