package com.sngular.skilltree.contract;

import java.util.List;

import com.sngular.skilltree.api.PeopleApi;
import com.sngular.skilltree.api.PersonApi;
import com.sngular.skilltree.api.model.CandidateDTO;
import com.sngular.skilltree.api.model.PatchedPeopleDTO;
import com.sngular.skilltree.api.model.PeopleDTO;
import com.sngular.skilltree.api.model.PeopleResumedDTO;
import com.sngular.skilltree.api.model.PositionDTO;
import com.sngular.skilltree.application.PeopleService;
import com.sngular.skilltree.application.updater.PeopleUpdater;
import com.sngular.skilltree.contract.mapper.CandidateMapper;
import com.sngular.skilltree.contract.mapper.PeopleMapper;
import com.sngular.skilltree.contract.mapper.PositionMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PeopleController implements PeopleApi, PersonApi {

  private final PeopleService peopleService;

  private final PeopleUpdater peopleUpdater;

  private final PeopleMapper peopleMapper;

  private final CandidateMapper candidateMapper;

  private final PositionMapper positionMapper;

    @Override
    public ResponseEntity<PeopleDTO> getPersonByCode(String peopleCode) {
        final var aux = peopleMapper
                .toPersonDTO(peopleService
                        .findByCode(peopleCode));
        return ResponseEntity.ok(aux);
    }

    @Override
    public ResponseEntity<Void> deletePerson(String peopleCode) {
        peopleService.deleteByCode(peopleCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<PeopleDTO> updatePerson(String peopleCode, PeopleDTO peopleDTO) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleUpdater
                        .update(peopleCode, peopleMapper
                                .toPerson(peopleDTO))));
    }

    @Override
    public ResponseEntity<PeopleDTO> patchPerson(String personCode, PatchedPeopleDTO patchedPeopleDTO) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleUpdater
                        .patch(personCode, peopleMapper
                                .toPeople(patchedPeopleDTO))));
    }

    @Override
    public ResponseEntity<List<PeopleDTO>> getPeople() {
        var people = peopleService.getAll();
        return ResponseEntity.ok(peopleMapper.toPeopleDto(people));
    }

    @Override
    public ResponseEntity<PeopleDTO> addPerson(PeopleDTO peopleDTO) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleService
                        .create(peopleMapper
                                .toPerson(peopleDTO))));
    }

    @Override
    public ResponseEntity<PeopleDTO> assignCandidate(String peopleCode, String positionCode) {
        return ResponseEntity.ok(peopleMapper
                .toPersonDTO(peopleService
                        .assignCandidate(peopleCode, positionCode)));
    }

    @Override
    public ResponseEntity<List<CandidateDTO>> getPeopleCandidates(String peopleCode) {
        return ResponseEntity.ok(candidateMapper
                .toCandidatesDTO(peopleService
                        .getCandidates(peopleCode)));
    }

    @Override
    public ResponseEntity<List<PeopleDTO>> getPeopleSkills(List<String> skillList){
        return ResponseEntity.ok(peopleMapper
                .toPeopleDto(peopleService
                        .getPeopleSkills(skillList)));
    }

    @Override
    public ResponseEntity<List<PeopleDTO>> getOtherPeopleStrategicSkills(String teamCode) {
        return ResponseEntity.ok(peopleMapper
                .toPeopleDto(peopleService
                        .getOtherPeopleStrategicSkills(teamCode)));
    }

    @Override
    public ResponseEntity<List<PositionDTO>> getPeopleAssignedPositions(String peopleCode) {
        return ResponseEntity.ok(positionMapper
                .toPositionsDTO(peopleService
                        .getPeopleAssignedPositions(peopleCode)));
    }

    @Override
    public ResponseEntity<List<PeopleResumedDTO>> getPeopleResume() {
        return ResponseEntity.ok(peopleMapper.toPeopleResumedDto(peopleService.getAllResumed()));
    }
}
