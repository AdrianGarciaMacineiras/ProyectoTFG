package com.tfg.skilltree.application.implement;

import java.util.List;
import java.util.Objects;

import com.tfg.skilltree.application.CandidateService;
import com.tfg.skilltree.application.PeopleService;
import com.tfg.skilltree.application.PositionService;
import com.tfg.skilltree.common.exceptions.EntityFoundException;
import com.tfg.skilltree.common.exceptions.EntityNotFoundException;
import com.tfg.skilltree.infraestructura.PeopleRepository;
import com.tfg.skilltree.model.Candidate;
import com.tfg.skilltree.model.People;
import com.tfg.skilltree.model.PeopleSkill;
import com.tfg.skilltree.model.Position;
import com.tfg.skilltree.model.views.PeopleNamesView;
import com.tfg.skilltree.model.views.PeopleView;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    private final CandidateService candidateService;

    private final PositionService positionService;

    @Override

    //@Cacheable(cacheNames = "people")
    public List<People> getAll() {
        return peopleRepository.findAll();
    }

    @Override
    //@Cacheable(cacheNames = "peopleView")
    public List<PeopleView> getAllResumed() {
        return peopleRepository.findAllResumed();
    }

    @Override
    //@Cacheable(cacheNames = "peopleNamesView")
    public List<PeopleNamesView> getAllNames() {
        return peopleRepository.findAllNames();
    }

    @Override
    //@CachePut(cacheNames = "people")
    //@CacheEvict(cacheNames = {"peopleView","peopleNamesView","people"})
    public People create(final People people) {
        validateExist(people.code());
        People.PeopleBuilder builder = people.toBuilder();
        People aux = builder.assignable(true).build();
        return peopleRepository.save(aux);
    }

    @Override
    //@Cacheable(cacheNames = "people", key = "#peopleCode")
    public People findByCode(final String peopleCode) {
        var people = peopleRepository.findByCode(peopleCode);
        if (Objects.isNull(people) || people.deleted())
            throw new EntityNotFoundException("People", peopleCode);
        var candidacies = candidateService.getCandidatesByPosition(peopleCode);
        if (!Objects.isNull(people.candidacies()))
            people.candidacies().clear();
        return people.toBuilder().candidacies(candidacies).build();
    }

    @Override
    //@Cacheable(cacheNames = "people", key = "#peopleCode")
    public People findPeopleByCode(final String peopleCode) {
        var people = peopleRepository.findPeopleByCode(peopleCode);
        if (Objects.isNull(people) || people.deleted())
            throw new EntityNotFoundException("People", peopleCode);
        return people;
    }

    @Override
    //@CacheEvict(cacheNames = "people", key = "#peopleCode")
    public boolean deleteByCode(final String peopleCode) {
        validateDoesNotExist(peopleCode);
        return peopleRepository.deleteByCode(peopleCode);
    }

    @Override
    public People assignCandidate(final String peopleCode, final String positionCode) {
        validateDoesNotExist(peopleCode);
        candidateService.assignCandidate(positionCode, peopleCode);
        return peopleRepository.findByCode(peopleCode);
    }

    @Override
    public List<Candidate> getCandidates(final String peopleCode) {
        validateDoesNotExist(peopleCode);
        return candidateService.getCandidatesByPeople(peopleCode);
    }

    @Override
    public List<People> getPeopleBySkills(final List<PeopleSkill> skills) {
        return peopleRepository.getPeopleBySkills(skills);
    }

    @Override
    public List<People> getOtherPeopleStrategicSkills(final String teamCode) {
        return peopleRepository.getOtherPeopleStrategicSkills(teamCode);
    }

    @Override
    public List<Position> getPeopleAssignedPositions(final String peopleCode) {
        validateDoesNotExist(peopleCode);
        return positionService.getPeopleAssignedPositions(peopleCode);
    }

    private void validateExist(final String code) {
        var oldPerson = peopleRepository.findByCode(code);
        if (!Objects.isNull(oldPerson) && !oldPerson.deleted()) {
            throw new EntityFoundException("People", code);
        }
    }

    private void validateDoesNotExist(final String code) {
        var oldPerson = peopleRepository.findByCode(code);
        if (Objects.isNull(oldPerson) || oldPerson.deleted()) {
            throw new EntityNotFoundException("People", code);
        }
    }
}