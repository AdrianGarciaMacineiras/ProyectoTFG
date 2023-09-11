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
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PeopleServiceImpl implements PeopleService {

    private final PeopleRepository peopleRepository;

    private final CandidateService candidateService;

    private final PositionService positionService;

    @Override
    @Cacheable(cacheNames = "people")
    public List<People> getAll() {
        return peopleRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = "peopleView")
    public List<PeopleView> getAllResumed() {
        return peopleRepository.findAllResumed();
    }

    @Override
    @CachePut(cacheNames = "people")
    public People create(final People people) {
        validateExist(people.code());
        People.PeopleBuilder builder = people.toBuilder();
        People aux = builder.assignable(true).build();
        return peopleRepository.save(aux);
    }

    @Override
    @Cacheable(cacheNames = "people", key = "#peopleCode")
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
    @Cacheable(cacheNames = "people", key = "#peopleCode")
    public People findPeopleByCode(final String peopleCode) {
        var people = peopleRepository.findPeopleByCode(peopleCode);
        if (Objects.isNull(people) || people.deleted())
            throw new EntityNotFoundException("People", peopleCode);
        return people;
    }

    @Override
    @CacheEvict(cacheNames = "people", key = "#peopleCode")
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
    public List<People> getPeopleBySkills(final List<String> skills) {
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
